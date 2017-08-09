package ru.devtron.republicperi.data;

import java.util.ArrayList;
import java.util.List;

import ru.devtron.republicperi.R;
import ru.devtron.republicperi.data.network.PlaceResponse;
import ru.devtron.republicperi.data.network.ServicesResponse;

public class CommonRepository {
    private static final CommonRepository ourInstance = new CommonRepository();
    private String[] images = new String[]{
            "https://images.unsplash.com/photo-1464746133101-a2c3f88e0dd9?dpr=2&auto=format&fit=crop&w=1500&h=1032&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1495320146009-0863560e378b?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1472132858735-6313c7962473?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1488572384981-eac03dfeb6b9?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1488572384981-eac03dfeb6b9?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1456693906521-44e96e49e85f?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1456693906521-44e96e49e85f?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1456693906521-44e96e49e85f?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1456693906521-44e96e49e85f?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop=",
            "https://images.unsplash.com/photo-1456693906521-44e96e49e85f?dpr=2&auto=format&fit=crop&w=1500&h=1000&q=80&cs=tinysrgb&crop="
    };

    public static CommonRepository getInstance() {
        return ourInstance;
    }

    private CommonRepository() {
    }

    public List<PlaceResponse> getNearestTours() {
        List<PlaceResponse> places = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            places.add(new PlaceResponse(i, "Место" + i, "Дербент", images[i]));
        }
        return places;
    }

    public List<ServicesResponse> getServices() {
        List<ServicesResponse> servicesResponses = new ArrayList<>();
        servicesResponses.add(new ServicesResponse(1, R.string.card_rent_bus, R.drawable.ic_directions_bus));
        servicesResponses.add(new ServicesResponse(2, R.string.card_rent_hotel, R.drawable.ic_hotel));
        servicesResponses.add(new ServicesResponse(3, R.string.card_guide_services, R.drawable.ic_guide));
        return servicesResponses;
    }
}
