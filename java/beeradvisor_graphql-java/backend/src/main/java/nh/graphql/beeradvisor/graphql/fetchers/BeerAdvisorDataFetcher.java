package nh.graphql.beeradvisor.graphql.fetchers;

import graphql.schema.DataFetcher;
import nh.graphql.beeradvisor.domain.BeerRepository;
import nh.graphql.beeradvisor.domain.Rating;
import nh.graphql.beeradvisor.domain.ShopRepository;
import nh.graphql.beeradvisor.domain.AddRatingInput;
import nh.graphql.beeradvisor.domain.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Component
public class BeerAdvisorDataFetcher {
    private final Logger logger = LoggerFactory.getLogger(BeerAdvisorDataFetcher.class);

    private final BeerRepository beerRepository;
    private final ShopRepository shopRepository;
    private final RatingService ratingService;

    @Autowired
    public BeerAdvisorDataFetcher(BeerRepository beerRepository, ShopRepository shopRepository, RatingService ratingService) {
        this.beerRepository = beerRepository;
        this.shopRepository = shopRepository;
        this.ratingService = ratingService;
    }

    public DataFetcher beerFetcher() {
        return environment -> {
            String beerId = environment.getArgument("beerId");
            return beerRepository.getBeer(beerId);
        };
    }

    public DataFetcher beersFetcher() {
        return environment -> beerRepository.findAll();
    }

    public DataFetcher shopFetcher() {
        return environment -> {
            String shopId = environment.getArgument("shopId");
            return shopRepository.findShop(shopId);
        };
    }

    public DataFetcher shopsFetcher() {
        return environment -> shopRepository.findAll();
    }

    public DataFetcher<Rating> addRatingFetcher() {
        return environment -> {
            // Input is always a Map, see: https://github.com/graphql-java/graphql-java/pull/782/files
            final Map<String, Object> ratingInput = environment.getArgument("ratingInput");
            AddRatingInput addRatingInput = new AddRatingInput();
            addRatingInput.setBeerId((String)ratingInput.get("beerId"));
            addRatingInput.setComment((String)ratingInput.get("comment"));
            addRatingInput.setStars((Integer)ratingInput.get("stars"));
            addRatingInput.setUserId((String)ratingInput.get("userId"));

            logger.debug("Rating Input", addRatingInput);
            return ratingService.addRating(addRatingInput);
        };
    }
}