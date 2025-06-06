package bbw.tm.backend.hotel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // Hotel erstellen
    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelCreateDTO hotelCreateDTO) {
        HotelDTO createdHotel = hotelService.saveHotel(hotelCreateDTO);
        return ResponseEntity.ok(createdHotel);
    }

    // Hotel teilweise aktualisieren
    @PatchMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotelPartially(@PathVariable Integer id, @RequestBody HotelDTO hotelDTO) {
        HotelDTO updatedHotel = hotelService.updateHotelPartially(id, hotelDTO);
        return ResponseEntity.ok(updatedHotel);
    }

    // Hotel löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Integer id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    // Alle Hotels anzeigen
    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<HotelDTO> hotels = hotelService.findAllHotels();
        return ResponseEntity.ok(hotels);
    }

    // Hotel nach ID finden
    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Integer id) {
        HotelDTO hotel = hotelService.findHotelById(id);
        return ResponseEntity.ok(hotel);
    }
}