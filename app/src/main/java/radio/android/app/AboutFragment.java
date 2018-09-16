package radio.android.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutFragment extends Fragment implements OnMapReadyCallback {

    public static final String TAG = AboutFragment.class.getSimpleName();
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.google_style));

        // Add a marker to me and move the camera
        LatLng shadhin = new LatLng(23.789474, 90.409103);
        mMap.addMarker(new MarkerOptions()
                .position(shadhin)
                .title("Shadhin 92.4FM")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.shadhin924fm_marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shadhin, 16));
    }
}
