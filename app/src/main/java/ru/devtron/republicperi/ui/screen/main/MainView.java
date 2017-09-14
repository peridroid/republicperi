package ru.devtron.republicperi.ui.screen.main;


import java.util.List;

import ru.devtron.republicperi.data.network.response.BaseResponse;
import ru.devtron.republicperi.ui.base.BaseView;

interface MainView extends BaseView {
    void initRecyclerView(int number, List<? extends BaseResponse> response);
}
