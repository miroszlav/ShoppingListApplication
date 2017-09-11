package hu.miroszlav.shoppinglistapplication.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.miroszlav.shoppinglistapplication.model.Item;
import io.reactivex.Observable;

@Singleton
public final class ItemService {

    private ApiService apiService;

    @Inject
    public ItemService(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<List<Item>> getItems() {
        return apiService.getAllItems();
    }

    public Observable<List<Item>> saveItem(Item item) {
        return apiService.saveItem(item);
    }
}
