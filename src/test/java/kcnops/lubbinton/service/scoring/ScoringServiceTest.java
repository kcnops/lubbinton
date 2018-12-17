package kcnops.lubbinton.service.scoring;

import kcnops.lubbinton.model.Match;
import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Round;
import kcnops.lubbinton.model.Setup;
import kcnops.lubbinton.model.Side;
import kcnops.lubbinton.service.scoring.ScoringService;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoringServiceTest {

	private ScoringService scoringService = new ScoringService();

	@Test
	public void testRest() {
		// Match is the same and not important such that score is all about rest
		final Match match = new Match(new Side(new Player(""), new Player("")), new Side(new Player(""), new Player("")));

		final Player kristof = new Player("Kristof");
		final Player thomas = new Player("Thomas");
		final Player lucas = new Player("Lucas");
		final Player smets = new Player("Smets");
		final List<Player> players = Arrays.asList(kristof, thomas, lucas, smets);

		final Round roundWithKristofResting = new Round(Collections.singletonList(match), Collections.singletonList(kristof));
		final Round roundWithThomasResting = new Round(Collections.singletonList(match), Collections.singletonList(thomas));
		final Round roundWithLucasResting = new Round(Collections.singletonList(match), Collections.singletonList(lucas));
		final Round roundWithSmetsResting = new Round(Collections.singletonList(match), Collections.singletonList(smets));

		final Setup allSameRest = new Setup(Arrays.asList(roundWithKristofResting, roundWithKristofResting, roundWithKristofResting, roundWithKristofResting));
		final Setup threeTimesSameRest = new Setup(Arrays.asList(roundWithKristofResting, roundWithKristofResting, roundWithKristofResting, roundWithThomasResting));
		final Setup twiceSameRest = new Setup(Arrays.asList(roundWithKristofResting, roundWithKristofResting, roundWithThomasResting, roundWithThomasResting));
		final Setup allDifferentRest = new Setup(Arrays.asList(roundWithKristofResting, roundWithThomasResting, roundWithLucasResting, roundWithSmetsResting));

		final int allSameRestScore = scoringService.score(allSameRest, players);
		final int threeTimesSameRestScore = scoringService.score(threeTimesSameRest, players);
		final int twiceSameRestScore = scoringService.score(twiceSameRest, players);
		final int allDifferentRestScore = scoringService.score(allDifferentRest, players);

		assertThat(allSameRestScore > threeTimesSameRestScore).isTrue();
		assertThat(threeTimesSameRestScore > twiceSameRestScore).isTrue();
		assertThat(twiceSameRestScore > allDifferentRestScore).isTrue();
	}

	@Test
	public void testWith() {
		final Player kristof = new Player("Kristof");
		final Player thomas = new Player("Thomas");
		final Player lucas = new Player("Lucas");
		final Player smets = new Player("Smets");
		final Player geert = new Player("Geert");
		final List<Player> players = Arrays.asList(kristof, thomas, lucas, smets, geert);

		final Player restPlayer = new Player("rest");
		final Player againstPlayer1 = new Player("against1");
		final Player againstPlayer2 = new Player("against2");
		final Side againstSide = new Side(againstPlayer1, againstPlayer2);

		final Match matchWithThomas = new Match(new Side(kristof, thomas), againstSide);
		final Match matchWithLucas = new Match(new Side(kristof, lucas), againstSide);
		final Match matchWithSmets = new Match(new Side(kristof, smets), againstSide);
		final Match matchWithGeert = new Match(new Side(kristof, geert), againstSide);

		final Round roundWithThomas = new Round(Collections.singletonList(matchWithThomas), Collections.singletonList(restPlayer));
		final Round roundWithLucas = new Round(Collections.singletonList(matchWithLucas), Collections.singletonList(restPlayer));
		final Round roundWithSmets = new Round(Collections.singletonList(matchWithSmets), Collections.singletonList(restPlayer));
		final Round roundWithGeert = new Round(Collections.singletonList(matchWithGeert), Collections.singletonList(restPlayer));

		final Setup allSameWith = new Setup(Arrays.asList(roundWithThomas, roundWithThomas, roundWithThomas, roundWithThomas));
		final Setup threeTimesSameWith = new Setup(Arrays.asList(roundWithThomas, roundWithThomas, roundWithThomas, roundWithLucas));
		final Setup twiceSameWith = new Setup(Arrays.asList(roundWithThomas, roundWithThomas, roundWithLucas, roundWithLucas));
		final Setup allDifferentWith = new Setup(Arrays.asList(roundWithThomas, roundWithLucas, roundWithSmets, roundWithGeert));

		final int allSameWithScore = scoringService.score(allSameWith, players);
		final int threeTimesSameWithScore = scoringService.score(threeTimesSameWith, players);
		final int twiceSameWithScore = scoringService.score(twiceSameWith, players);
		final int allDifferentWithScore = scoringService.score(allDifferentWith, players);

		assertThat(allSameWithScore > threeTimesSameWithScore).isTrue();
		assertThat(threeTimesSameWithScore > twiceSameWithScore).isTrue();
		assertThat(twiceSameWithScore > allDifferentWithScore).isTrue();
	}

	@Test
	public void testAgainst() {
		final Player kristof = new Player("Kristof");
		final Player thomas = new Player("Thomas");
		final Player lucas = new Player("Lucas");
		final Player smets = new Player("Smets");
		final Player geert = new Player("Geert");
		final List<Player> players = Arrays.asList(kristof, thomas, lucas, smets, geert);

		final Player restPlayer = new Player("rest");
		final Player withPlayer1 = new Player("with1");
		final Side withSide = new Side(kristof, withPlayer1);
		final Player withPlayer2 = new Player("with2");

		final Match matchAgainstThomas = new Match(withSide, new Side(thomas, withPlayer2));
		final Match matchAgainstLucas = new Match(withSide, new Side(lucas, withPlayer2));
		final Match matchAgainstSmets = new Match(withSide, new Side(smets, withPlayer2));
		final Match matchAgainstGeert = new Match(withSide, new Side(geert, withPlayer2));

		final Round roundAgainstThomas = new Round(Collections.singletonList(matchAgainstThomas), Collections.singletonList(restPlayer));
		final Round roundAgainstLucas = new Round(Collections.singletonList(matchAgainstLucas), Collections.singletonList(restPlayer));
		final Round roundAgainstSmets = new Round(Collections.singletonList(matchAgainstSmets), Collections.singletonList(restPlayer));
		final Round roundAgainstGeert = new Round(Collections.singletonList(matchAgainstGeert), Collections.singletonList(restPlayer));

		final Setup allSameAgainst = new Setup(Arrays.asList(roundAgainstThomas, roundAgainstThomas, roundAgainstThomas, roundAgainstThomas));
		final Setup threeTimesSameAgainst = new Setup(Arrays.asList(roundAgainstThomas, roundAgainstThomas, roundAgainstThomas, roundAgainstLucas));
		final Setup twiceSameAgainst = new Setup(Arrays.asList(roundAgainstThomas, roundAgainstThomas, roundAgainstLucas, roundAgainstLucas));
		final Setup allDifferentAgainst = new Setup(Arrays.asList(roundAgainstThomas, roundAgainstLucas, roundAgainstSmets, roundAgainstGeert));

		final int allSameAgainstScore = scoringService.score(allSameAgainst, players);
		final int threeTimesSameAgainstScore = scoringService.score(threeTimesSameAgainst, players);
		final int twiceSameAgainstScore = scoringService.score(twiceSameAgainst, players);
		final int allDifferentAgainstScore = scoringService.score(allDifferentAgainst, players);

		assertThat(allSameAgainstScore > threeTimesSameAgainstScore).isTrue();
		assertThat(threeTimesSameAgainstScore > twiceSameAgainstScore).isTrue();
		assertThat(twiceSameAgainstScore > allDifferentAgainstScore).isTrue();
	}

//	@Test
//	public void testRestIsMostImportant() {
//		final Player a = new Player("a");
//		final Player b = new Player("b");
//		final Player c = new Player("c");
//		final Player d = new Player("d");
//		final Player e = new Player("e");
//		final Player f = new Player("f");
//		final Player g = new Player("g");
//		final Player h = new Player("h");
//		final Player i = new Player("i");
//		final Player j = new Player("j");
//		final Player k = new Player("k");
//		final Player l = new Player("l");
//		final Player m = new Player("m");
//		final Round round = new Round(Arrays.asList(new Match(new Side(a, b), new Side(c, d)), new Match(new Side(e, f), new Side(g, h)), new Match(new Side(i, j), new Side(k, l))), Collections.singletonList(m));
//
//	}
}