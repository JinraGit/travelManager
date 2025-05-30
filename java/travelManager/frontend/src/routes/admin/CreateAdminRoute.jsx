import {useState} from "react";
import {createAdmin} from "@/lib/users/admins";
import {useNavigate} from "react-router";

export default function CreateAdminRoute() {
    const [form, setForm] = useState({username: "", email: "", password: ""});
    const [validated, setValidated] = useState(false);
    const [status, setStatus] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formElement = e.currentTarget;
        if (!formElement.checkValidity()) {
            e.stopPropagation();
            setValidated(true);
            return;
        }

        try {
            await createAdmin(form);
            navigate("/admin/manage-accounts");
            setStatus("Admin wurde erfolgreich erstellt.");
            setError(null);
            setForm({username: "", email: "", password: ""});
            setValidated(false);
        } catch (err) {
            if (err.message.includes("409")) {
                setError("E-Mail oder Benutzername ist bereits vergeben.");
            } else {
                setError("Fehler beim Erstellen des Admin-Kontos.");
            }
            setStatus(null);
        }
    };

    return (
        <div className="container py-5">
            <div className="row justify-content-center">
                <div className="col-lg-7 col-xl-6">
                    <div className="card shadow border-0 rounded-4">
                        <div className="card-body p-4">
                            <h2 className="text-center text-success mb-4">Neuen Admin erstellen</h2>
                            <form
                                noValidate
                                className={validated ? "was-validated" : ""}
                                onSubmit={handleSubmit}
                            >
                                <div className="mb-3">
                                    <label htmlFor="username" className="form-label">Benutzername</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="username"
                                        name="username"
                                        value={form.username}
                                        onChange={handleChange}
                                        required
                                    />
                                    <div className="invalid-feedback">Benutzername ist erforderlich.</div>
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="email" className="form-label">E-Mail-Adresse</label>
                                    <input
                                        type="email"
                                        className="form-control"
                                        id="email"
                                        name="email"
                                        value={form.email}
                                        onChange={handleChange}
                                        required
                                    />
                                    <div className="invalid-feedback">Gültige E-Mail ist erforderlich.</div>
                                </div>

                                <div className="mb-4">
                                    <label htmlFor="password" className="form-label">Passwort</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        id="password"
                                        name="password"
                                        value={form.password}
                                        onChange={handleChange}
                                        required
                                        minLength={8}
                                    />
                                    <div className="invalid-feedback">Mindestens 8 Zeichen.</div>
                                </div>

                                <div className="d-grid">
                                    <button type="submit" className="btn btn-success">
                                        Admin erstellen
                                    </button>
                                </div>
                            </form>

                            {status && (
                                <div className="alert alert-success mt-4 text-center">
                                    {status}
                                </div>
                            )}
                            {error && (
                                <div className="alert alert-danger mt-4 text-center">
                                    {error}
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
