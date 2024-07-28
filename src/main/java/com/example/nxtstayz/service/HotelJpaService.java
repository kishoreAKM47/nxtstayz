/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.nxtstayz.service;

import com.example.nxtstayz.model.*;
import com.example.nxtstayz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelJpaService implements HotelRepository {
    @Autowired
    private HotelJpaRepository hotelJpaRepository;
    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Override
    public ArrayList<Hotel> getHotels() {
        List<Hotel> hotellist = hotelJpaRepository.findAll();
        ArrayList<Hotel> hotels = new ArrayList<>(hotellist);
        return hotels;
    }

    @Override
    public Hotel getHotelById(int hotelId) {
        try {
            Hotel hotel = hotelJpaRepository.findById(hotelId).get();
            return hotel;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Hotel addHotel(Hotel hotel) {
        hotelJpaRepository.save(hotel);
        return hotel;
    }

    @Override
    public Hotel updateHotel(int hotelId, Hotel hotel) {
        try {
            Hotel newhotel = hotelJpaRepository.findById(hotelId).get();
            if (hotel.getHotelName() != null) {
                newhotel.setHotelName(hotel.getHotelName());
            }
            if (hotel.getLocation() != null) {
                newhotel.setLocation(hotel.getLocation());
            }
            if (hotel.getRating() != 0) {
                newhotel.setRating(hotel.getRating());
            }
            return hotelJpaRepository.save(newhotel);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteHotel(int hotelId) {
        try {
            Hotel hotel = hotelJpaRepository.findById(hotelId).get();
            List<Room> roomlist = roomJpaRepository.findByHotel(hotel);
            for (Room room : roomlist) {
                room.setHotel(null);
            }
            roomJpaRepository.saveAll(roomlist);
            hotelJpaRepository.deleteById(hotelId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Room> getHotelRoom(int hotelId) {
        try {
            Hotel hotel = hotelJpaRepository.findById(hotelId).get();
            return roomJpaRepository.findByHotel(hotel);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}