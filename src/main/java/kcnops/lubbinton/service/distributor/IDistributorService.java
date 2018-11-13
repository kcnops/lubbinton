package kcnops.lubbinton.service.distributor;

import kcnops.lubbinton.model.Player;
import kcnops.lubbinton.model.Setup;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

@FunctionalInterface
public interface IDistributorService {

	@Nonnull
	Set<Setup> distribute(@Nonnull final List<Player> players, final int amountOfRounds);

}
