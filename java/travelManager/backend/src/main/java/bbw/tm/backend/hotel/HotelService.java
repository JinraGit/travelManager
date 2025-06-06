package bbw.tm.backend.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelDTO saveHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.fromDTO(hotelDTO);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDTO(savedHotel);
    }

    public HotelDTO updateHotel(Integer id, HotelDTO hotelDTO) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        hotel.setName(hotelDTO.name());
        hotel.setCheckInDate(hotelDTO.checkInDate());
        hotel.setCheckOutDate(hotelDTO.checkOutDate());
        hotel.setPrice(hotelDTO.price());
        hotel.setAddress(hotelMapper.fromDTO(hotelDTO).getAddress());
        
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