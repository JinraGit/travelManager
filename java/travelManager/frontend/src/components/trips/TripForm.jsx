export default function TripForm({ form, onChange, onSubmit }) {
    if (!form) return <div className="mt-4">Lade Reisedaten ...</div>;

    return (
        <form onSubmit={onSubmit}>
            {/* Reisetyp */}
            <div className="mb-3">
                <label>Reisetyp</label>
                <select name="tripType" className="form-control" value={form.tripType} onChange={onChange}>
                    <option value="PRIVATE">Privat</option>
                    <option value="BUSINESS">Geschäftlich</option>
                </select>
            </div>

            {/* Start-/Ende */}
            <div className="mb-3">
                <label>Startdatum</label>
                <input type="date" name="startDate" className="form-control" value={form.startDate} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Enddatum</label>
                <input type="date" name="endDate" className="form-control" value={form.endDate} onChange={onChange} />
            </div>

            {/* Transport */}
            <h4>Transport</h4>
            <div className="mb-3">
                <label>Typ</label>
                <select name="transport.type" className="form-control" value={form.transport.type} onChange={onChange}>
                    <option value="CAR">Auto</option>
                    <option value="TRAIN">Zug</option>
                    <option value="AIRPLANE">Flugzeug</option>
                    <option value="BUS">Bus</option>
                </select>
            </div>
            <div className="mb-3">
                <label>Datum</label>
                <input type="date" name="transport.date" className="form-control" value={form.transport.date} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Abfahrt (HH:MM)</label>
                <input type="time" name="transport.departureTime" className="form-control" value={form.transport.departureTime} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Ankunft (HH:MM)</label>
                <input type="time" name="transport.arrivalTime" className="form-control" value={form.transport.arrivalTime} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Details</label>
                {form.transport.type === "CAR" && (
                    <input type="text" name="transport.licensePlate" className="form-control" placeholder="Kennzeichen" value={form.transport.licensePlate} onChange={onChange} />
                )}
                {form.transport.type === "TRAIN" && (
                    <input type="text" name="transport.trainNumber" className="form-control" placeholder="Zugnummer" value={form.transport.trainNumber} onChange={onChange} />
                )}
                {form.transport.type === "AIRPLANE" && (
                    <input type="text" name="transport.airline" className="form-control" placeholder="Fluggesellschaft" value={form.transport.airline} onChange={onChange} />
                )}
                {form.transport.type === "BUS" && (
                    <input type="text" name="transport.busNumber" className="form-control" placeholder="Busnummer" value={form.transport.busNumber} onChange={onChange} />
                )}
            </div>

            {/* Hotel */}
            <h4>Hotel</h4>
            <div className="mb-3">
                <label>Name</label>
                <input type="text" name="hotel.name" className="form-control" value={form.hotel.name} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Adresse</label>
                <input type="text" name="hotel.address.street" className="form-control mb-1" placeholder="Strasse" value={form.hotel.address.street} onChange={onChange} />
                <input type="text" name="hotel.address.houseNumber" className="form-control mb-1" placeholder="Hausnummer" value={form.hotel.address.houseNumber} onChange={onChange} />
                <input type="text" name="hotel.address.city" className="form-control mb-1" placeholder="Ort" value={form.hotel.address.city} onChange={onChange} />
                <input type="text" name="hotel.address.zipCode" className="form-control mb-1" placeholder="PLZ" value={form.hotel.address.zipCode} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Check-In</label>
                <input type="date" name="hotel.checkInDate" className="form-control" value={form.hotel.checkInDate} onChange={onChange} />
            </div>
            <div className="mb-3">
                <label>Check-Out</label>
                <input type="date" name="hotel.checkOutDate" className="form-control" value={form.hotel.checkOutDate} onChange={onChange} />
            </div>

            <button type="submit" className="btn btn-primary">Speichern</button>
        </form>
    );
}
