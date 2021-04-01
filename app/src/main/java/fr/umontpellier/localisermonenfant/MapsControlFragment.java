package fr.umontpellier.localisermonenfant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import static fr.umontpellier.localisermonenfant.utils.CustomMarker.createCustomMarker;

public class MapsControlFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            float zoom = googleMap.getCameraPosition().zoom;
            LatLng montpellierLocation = new LatLng(43.61, 3.87);
            googleMap.addMarker(new MarkerOptions().position(montpellierLocation).title("Montpellier"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(montpellierLocation));

            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

                    LatLng customMarkerLocationOne = new LatLng(43.611900, 3.877200);
                    LatLng customMarkerLocationTwo = new LatLng(43.622820, 3.877236);
                    LatLng customMarkerLocationThree = new LatLng(43.644820, 3.917236);
                    LatLng customMarkerLocationFour = new LatLng(43.685820, 3.897236);

                    googleMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(getActivity(),R.drawable.fille1,"Manish")))).
                            setTitle("Aminata");
                    googleMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(getActivity(),R.drawable.fille2,"Lina")))).
                            setTitle("Lina");
                    googleMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(getActivity(),R.drawable.fille3,"Isa")))).
                            setTitle("Isa");
                    googleMap.addMarker(new MarkerOptions().position(customMarkerLocationFour).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(getActivity(),R.drawable.fille4,"Jean")))).
                            setTitle("Jean");

                    //LatLngBound will cover all your marker on Google Maps
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
                    builder.include(customMarkerLocationThree); //Taking Point B (Second LatLng)
                    builder.include(customMarkerLocationTwo); //Taking Point A (First LatLng)
                    builder.include(customMarkerLocationFour);
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 300);
                    googleMap.moveCamera(cu);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(montpellierLocation.latitude, montpellierLocation.longitude), 12), null);
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}