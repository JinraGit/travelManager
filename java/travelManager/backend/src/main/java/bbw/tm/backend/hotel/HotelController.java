package bbw.tm.backend.hotel;

import bbw.tm.backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // 1. Hotel erstellen
    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        HotelDTO createdHotel = hotelService.saveHotel(hotelDTO);
        return ResponseEntity.ok(createdHotel);
    }

    // 2. Hotel bearbeiten
    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Integer id, @RequestBody HotelDTO hotelDTO) {
        HotelDTO updatedHotel = hotelService.updateHotel(id, hotelDTO);
        return ResponseEntity.ok(updatedHotel);
    }

    // 3. Hotel löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Integer id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Alle Hotels anzeigen
    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.findAllHotels();
        return ResponseEntity.ok(hotels);
    }

    // 5. Hotel nach ID finden
    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Integer id) {
        HotelDTO hotel = hotelService.findHotelById(id);
        return ResponseEntity.ok(hotel);
    }
}