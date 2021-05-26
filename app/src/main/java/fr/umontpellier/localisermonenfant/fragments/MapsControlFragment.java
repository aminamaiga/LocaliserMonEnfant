package fr.umontpellier.localisermonenfant.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.models.Zone;
import fr.umontpellier.localisermonenfant.networks.businesses.BussnessServiceZone;
import fr.umontpellier.localisermonenfant.utils.PrefUserInfos;

import static fr.umontpellier.localisermonenfant.networks.businesses.BusinessServiceChild.USERDATA;
import static fr.umontpellier.localisermonenfant.utils.CustomMarker.createCustomMarker;

public class MapsControlFragment extends Fragment {
    Context context;
    MapViewModel mapViewModel;
    String TAG = "MapsControlFragment";
    GoogleMap googleMap;
    Marker mMarker;
    LatLng customMarkerLocationOne;
    List<LatLng> encludePosition = new ArrayList<>();
    int zoneTypeSelected = -1;
    final List<Integer> photoTest = Arrays.asList(R.drawable.fille1, R.drawable.fille2, R.drawable.fille3, R.drawable.fille4);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private OnMapReadyCallback callback = new MapView();

    class MapView implements OnMapReadyCallback {

        int tab[] = {R.drawable.fille1, R.drawable.fille2, R.drawable.fille3, R.drawable.fille4};

        @Override
        public void onMapReady(GoogleMap _googleMap) {
            googleMap = _googleMap;
            LatLng montpellierLocation = new LatLng(43.61, 3.87);
            googleMap.addMarker(new MarkerOptions().position(montpellierLocation).title("Montpellier"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(montpellierLocation));

            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

                    customMarkerLocationOne = new LatLng(43.611900, 3.877200);
                    mMarker = googleMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).
                            icon(BitmapDescriptorFactory.fromBitmap(
                                    createCustomMarker(getActivity(), R.drawable.fille1, "Manish"))));
                    mMarker.setTitle("Aminata");
                    mMarker.setTag("");
                    mMarker.showInfoWindow();

                    //LatLngBound will cover all your marker on Google Maps
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 300);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    googleMap.moveCamera(cu);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(montpellierLocation.latitude, montpellierLocation.longitude), 12), null);
                }
            });

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng arg0) {
                    android.util.Log.i("onMapClick", "Horray!");
                    parameter(arg0);
                }

            });

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    mapViewModel.getAlltrajet(marker.getTag().toString());
                    Log.d(TAG, "onMarkerClick: " + marker.getTag().toString());
                    return true;
                }
            });

            googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    // Flip the r, g and b components of the circle's stroke color.
                    int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                    circle.setStrokeColor(strokeColor);
                }
            });
        }
    }

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

        mapViewModel = new ViewModelProvider(requireActivity(), new ViewModelFactoryMap(context)).get(MapViewModel.class);
        mapViewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<Trajet>() {
            @Override
            public void onChanged(Trajet trajet) {
                if (mMarker != null) {
                    //recuperation de la photo à corriger un bug
                    int photoId = mapViewModel.postion.getValue();
                    if (photoId > 3) {
                        photoId = 0;
                    }
                    Log.e(TAG, "onChanged: " + trajet.getLatitude() + " " + trajet.getLongitute());
                    LatLng newPosition = new LatLng(trajet.getLatitude(), trajet.getLongitute());
                    mMarker.setDraggable(false);
                    mMarker.setTag(trajet.getChild());
                    mMarker.setIcon((BitmapDescriptorFactory.fromBitmap(
                            createCustomMarker(getActivity(), photoTest.get(photoId), mapViewModel.clickedChild.getValue().getFirstname()))));
                    mMarker.setTitle(mapViewModel.clickedChild.getValue().getFirstname());
                    animateMarker(mMarker, newPosition, false);
                    mMarker.setPosition(newPosition);
                    mMarker.showInfoWindow();
                    encludePosition.add(newPosition);
                }
            }
        });

        mapViewModel.getAllTrajet().observe(getViewLifecycleOwner(), new Observer<List<Trajet>>() {
            @Override
            public void onChanged(List<Trajet> trajets) {
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.width(10)
                        .color(Color.RED)
                        .geodesic(true);
                Log.e(TAG, "onChanged list Trajet: " + trajets.size());
                for (Trajet trajet : trajets) {
                    encludePosition.add(new LatLng(trajet.getLatitude(), trajet.getLongitute()));
                    polylineOptions.addAll(encludePosition); // Closes the polyline.
                }
                // Get back the mutable Polyline
                googleMap.addPolyline(polylineOptions);
                zoomToShowBothPoints(encludePosition);
            }
        });
    }

    private void zoomToShowBothPoints(List<LatLng> newPositions) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : newPositions) {
            builder.include(latLng);
        } //Taking Point A (First LatLng)
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 220);
        googleMap.moveCamera(cu);
        if (encludePosition.isEmpty() == false)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(encludePosition.get(0), 12), null);
    }

    public void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
                zoomToShowBothPoints(encludePosition);
            }
        });
    }

    public void parameter(LatLng zonePosition) {
        String[] zoneItems = new String[]{"très sensible", "sensible", "moyen", "normal"};
        int[] colors = new int[]{Color.RED, Color.YELLOW, Color.MAGENTA, Color.GREEN};
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setSingleChoiceItems(zoneItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zoneTypeSelected = which;
            }
        }).setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (zoneTypeSelected == -1) {
                    Toast.makeText(context, "Merci de selectionner le niveau de sensibilité", Toast.LENGTH_LONG).show();
                } else {
                    Marker zoneMarker = googleMap.addMarker(new MarkerOptions().position(zonePosition).title("Zone"));
                    encludePosition.add(zonePosition);
                    googleMap.addCircle(new CircleOptions()
                            .center(zonePosition)
                            .radius(500)
                            .strokeWidth(10)
                            .strokeColor(getResources().getColor(R.color.colorPrimary))
                            .fillColor(colors[zoneTypeSelected])
                            .clickable(true));
                    zoneMarker.setDraggable(false);
                    zoneMarker.setTag("");
                    animateMarker(zoneMarker, zonePosition, false);

                    String parentId = null;
                    PrefUserInfos prefUserInfos = new PrefUserInfos(context);
                    JSONObject userObject = prefUserInfos.loadMap(USERDATA);
                    try {
                        String userId = userObject.getString("userId");
                        parentId = userId.replaceAll("\"", "");
                        Log.i(TAG, "register: userObject " + userId.replaceAll("\"", ""));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Zone zone = new Zone(zonePosition.latitude, zonePosition.longitude, parentId, 5.0, new Date(), zoneTypeSelected);
                    BussnessServiceZone bussnessServiceZone = new BussnessServiceZone(context);
                    bussnessServiceZone.initializeAuthentificatedCommunication();
                    bussnessServiceZone.save(zone);
                }
            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context, "Modification annulée", Toast.LENGTH_LONG).show();
            }
        });

        materialAlertDialogBuilder.create();
        materialAlertDialogBuilder.show();
    }
}