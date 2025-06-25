package bbw.tm.backend.hotel;

import bbw.tm.backend.trip.Trip;
import bbw.tm.backend.trip.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;


    public HotelDTO saveHotel(HotelCreateDTO hotelCreateDTO) {
        Hotel hotel = hotelMapper.fromCreateDTO(hotelCreateDTO);

        // Trip-Verknüpfung entfällt, Trip-IDs sind nicht mehr Teil von HotelCreateDTO
        // Das Hotel wird direkt gespeichert, ohne manuelle Trip-Zuweisung
        Hotel savedHotel = hotelRepository.save(hotel);

        return hotelMapper.toDTO(savedHotel);
    }


    public HotelDTO updateHotelPartially(Integer id, HotelDTO hotelDTO) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        hotelMapper.updateFromDTO(hotel, hotelDTO);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDTO(updatedHotel);
    }

    public void deleteHotel(Integer id) {
        if (!hotelRepository.existsById(id)) {
            throw new IllegalArgumentException("Hotel not found");
        }
        hotelRepository.deleteById(id);
    }

    public HotelDTO findHotelById(Integer id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        return hotelMapper.toDTO(hotel);
    }

    public List<HotelDTO> findAllHotels() {
        return hotelRepository.findAll().stream().map(hotelMapper::toDTO).collect(Collectors.toList());
    }
}