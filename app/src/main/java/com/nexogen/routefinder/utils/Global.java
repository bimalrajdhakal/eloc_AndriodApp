package com.nexogen.routefinder.utils;

import android.util.Log;

import com.nexogen.routefinder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nexogen on 15/12/17.
 */

public class Global {

    public static int[] placesImages = new int[]{R.drawable.icon_1_accounting, R.drawable.icon_2_airport, R.drawable.icon_3_amusementpark, R.drawable.icon_4_aquarium, R.drawable.icon_5_artgallery, R.drawable.icon_6_atm, R.drawable.icon_7_backery, R.drawable.icon_8_bank, R.drawable.icon_9_bar, R.drawable.icon_10_beauti_salloon, R.drawable.icon_11_bicycle, R.drawable.icon_12_book_store, R.drawable.icon_13_bowling_alley, R.drawable.icon_14_bus_stand, R.drawable.icon_15_cafe, R.drawable.icon_16_campground, R.drawable.icon_17_car_dealar, R.drawable.icon_18_car_rental, R.drawable.icon_19_car_repair, R.drawable.icon_20_car_wash, R.drawable.icon_21_casino,
            R.drawable.icon_22_cemetery, R.drawable.icon_23_church, R.drawable.icon_24_city_hall, R.drawable.icon_25_clothing_store, R.drawable.icon_26_convience_store, R.drawable.icon_27_court_house, R.drawable.icon_28_dentist, R.drawable.icon_29_department_store, R.drawable.icon_30_doctor, R.drawable.icon_31_electrician, R.drawable.icon_32_elecronic_store, R.drawable.icon_33_embassy, R.drawable.icon_34_establishment, R.drawable.icon_35_finance, R.drawable.icon_36_fire_station, R.drawable.icon_37_florist,
            R.drawable.icon_38_food, R.drawable.icon_39_funeral_home, R.drawable.icon_40_furniture_store, R.drawable.icon_41_gas_station, R.drawable.icon_42_general_contractor, R.drawable.icon_43_gorocery_or_super_market, R.drawable.icon_44_gym, R.drawable.icon_45_hair_care, R.drawable.icon_46_hardware_store, R.drawable.icon_47_health, R.drawable.icon_48_hindu_temple, R.drawable.icon_49_home_goods_shop, R.drawable.icon_50_hospital, R.drawable.icon_51_insurance_agency, R.drawable.icon_52_jewelry, R.drawable.icon_53_laundry, R.drawable.icon_54_lawyer, R.drawable.icon_55_library, R.drawable.icon_56_liquor_store, R.drawable.icon_57_local_goverment_office,
            R.drawable.icon_58_lock_smith, R.drawable.icon_59_lodging, R.drawable.icon_60_meal_delevary, R.drawable.icon_61_meal_takeaway, R.drawable.icon_62_mosque, R.drawable.icon_63_movie_rentle, R.drawable.icon_64_movie_theater, R.drawable.icon_65_moving_company, R.drawable.icon_66_museum_icon, R.drawable.icon_67_night_club, R.drawable.icon_68_painter, R.drawable.icon_69_park, R.drawable.icon_70_parking, R.drawable.icon_71_pat_store, R.drawable.icon_72_pharmacy, R.drawable.icon_73_physio_theropy, R.drawable.icon_74_place_of_worship, R.drawable.icon_75_plumber, R.drawable.icon_76_police, R.drawable.icon_77_post_office, R.drawable.icon_78_real_state_agency, R.drawable.icon_79_resturent, R.drawable.icon_80_roofing_contractor, R.drawable.icon_81_rv_park, R.drawable.icon_82_school,
            R.drawable.icon_83_shoe_store, R.drawable.icon_84_shopping_mall, R.drawable.icon_85_spa, R.drawable.icon_86_stadium, R.drawable.icon_87_storage, R.drawable.icon_88_store, R.drawable.icon_89_subway_station, R.drawable.icon_90_synagogue, R.drawable.icon_91_taxi_stand, R.drawable.icon_92_train_station, R.drawable.icon_93_travel_agency, R.drawable.icon_94_university, R.drawable.icon_95_veteniry, R.drawable.icon_96_zoo};

    public static String sampleDateFormat() {
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy \n hh:mm a");
        String Date = simpledateformat.format(calander.getTime());

        Log.d("date", Date);
        return Date;
    }

}
