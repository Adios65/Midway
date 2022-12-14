package com.example.projetintegrateur.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetintegrateur.R;
import com.example.projetintegrateur.adapter.BusinessRecyclerViewAdapter;
import com.example.projetintegrateur.model.BusinessModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BusinessDialog extends BottomSheetDialogFragment implements BusinessRecyclerViewAdapter.DataTransferInterfaceRecycler {

    ArrayList<BusinessModel> businessData;
    LatLng selectedBusinnesCoordinates;
    String selectedBusinessAddressName;
    String selectedBusinessName;
    String selectedBusinessPhoto;
    RecyclerView recyclerView;
    BusinessRecyclerViewAdapter adapterFilter;

    DataTransferInterfaceDialog mListener;

    public BusinessDialog(ArrayList<BusinessModel> businessData, DataTransferInterfaceDialog mListener) {

        this.businessData = businessData;
        this.mListener = mListener;

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View myFormView = inflater.inflate(R.layout.business_dialog_layout, container, false);

        TextView btn_resto = myFormView.findViewById(R.id.textview_restaurants);
        TextView btn_bar = myFormView.findViewById(R.id.textview_bars);
        TextView btn_cafe = myFormView.findViewById(R.id.textview_cafes);
        TextView btn_all = myFormView.findViewById(R.id.textview_all);

        String[] restaurantsTypes = new String[2];
        restaurantsTypes[0] = "restaurant";
        restaurantsTypes[1] = "food";


        String[] barTypes = new String[2];
        barTypes[0] = "bar";
        barTypes[1] = " ";

        String[] cafeTypes = new String[2];
        cafeTypes[0] = "cafe";
        cafeTypes[1] = " ";

        String[] allType = new String[2];
        allType[0] = "all";
        allType[1] = " ";

        btn_resto.setOnClickListener(v -> filterResultBy(restaurantsTypes, myFormView.getContext()));
        btn_bar.setOnClickListener(v -> filterResultBy(barTypes, myFormView.getContext()));
        btn_cafe.setOnClickListener(v -> filterResultBy(cafeTypes, myFormView.getContext()));
        btn_all.setOnClickListener(v -> filterResultBy(allType, myFormView.getContext()));

        //CREATE RECYCLERVIEW
        recyclerView = myFormView.findViewById(R.id.recycleView_business);

        //CREATE ADAPTER FOR RECYCLERVIEW CONTENT
        BusinessRecyclerViewAdapter adapter = new BusinessRecyclerViewAdapter(myFormView.getContext(), businessData, this);

        //SET ADAPTER AND SET THE LAYOUTMANAGER
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(myFormView.getContext()));


        return myFormView;
    }

    public void filterResultBy(String[] types, Context context) {
        ArrayList<BusinessModel> newTypeList = new ArrayList<>();

        for (int i=0; i< types.length; i++) {
            if (types[i].equals("all")) {
                adapterFilter = new BusinessRecyclerViewAdapter(context, businessData, BusinessDialog.this);

            } else {
                for (BusinessModel uniqueBusiness : businessData) {
                    if (uniqueBusiness.getTypes().contains(types[i])) {
                        newTypeList.add(uniqueBusiness);
                    }
                }

                if (newTypeList.size() == 0) {
                    BusinessModel noResultBusiness = new BusinessModel("No Results", "", "", null, null, null);
                    newTypeList.add(noResultBusiness);
                }
                adapterFilter = new BusinessRecyclerViewAdapter(context, newTypeList, BusinessDialog.this);
            }
        }

        recyclerView.swapAdapter(adapterFilter, false);

    }

    @Override
    public void getSelectedBusinnes(LatLng businessCoordinate, String businessAddressName, String businessName, String businessPhoto) {
        selectedBusinnesCoordinates = businessCoordinate;
        selectedBusinessAddressName = businessAddressName;
        selectedBusinessName = businessName;
        selectedBusinessPhoto = businessPhoto;
        mListener.getSelectedBusinnes(selectedBusinnesCoordinates, selectedBusinessAddressName, businessName, selectedBusinessPhoto);


    }

    public interface DataTransferInterfaceDialog {
        public void getSelectedBusinnes(LatLng businessCoordinate, String businessAddressName, String businessName, String businessPhoto);
    }
}
