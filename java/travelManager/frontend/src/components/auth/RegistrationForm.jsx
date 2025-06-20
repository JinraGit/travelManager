import {useState} from "react";
import styles from "./Auth.module.css";
import {signUp} from "@/lib/auth/auth";
import {useNavigate} from "react-router";

export default function AuthForm() {
    const [form, setForm] = useState({email: "", username: "", password: ""});
    const [errors, setErrors] = useState({});
    const [submitError, setSubmitError] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
        setErrors({...errors, [e.target.name]: ""});
        setSubmitError("");
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const newErrors = {};
        if (!form.email.includes("@")) newErrors.email = "Gib eine gültige E-Mail ein.";
        if (form.password.length < 8) newErrors.password = "Mindestens 8 Zeichen erforderlich.";
        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
            return;
        }

        try {
            await signUp(form);
            navigate("/");
        } catch (err) {
            setSubmitError("Login fehlgeschlagen. E-Mail oder Passwort ist ungültig.");
        }
    };

    return (
        <div className={styles.wrapper}>
            <div className={`card p-4 shadow ${styles.card}`}>
                <img src="/assets/tm_logo.png" alt="Logo" className={styles.logo}/>
                <h2 className={styles.title}>Registrierung</h2>

                {submitError && (
                    <div className="alert alert-danger mt-2 mb-3" role="alert">
                        {submitError}
                    </div>
                )}

                <form onSubmit={handleSubmit} noValidate>
                    <div className="mb-3">
                        <label className="form-label">Benutzername</label>
                        <input
                            name="username"
                            type="text"
                            className={`form-control ${errors.username ? "is-invalid" : ""}`}
                            value={form.username}
                            onChange={handleChange}
                            placeholder="Gib deinen Benutzernamen ein"
                        />
                        {errors.username && <div className="invalid-feedback">{errors.username}</div>}
                    </div>

                    <div className="mb-3">
                        <label className="form-label">E-Mail</label>
                        <input
                            name="email"
                            type="email"
                            className={`form-control ${errors.email ? "is-invalid" : ""}`}
                            value={form.email}
                            onChange={handleChange}
                            placeholder="Gib deine E-Mail ein"
                        />
                        {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Passwort</label>
                        <input
                            name="password"
                            type="password"
                            className={`form-control ${errors.password ? "is-invalid" : ""}`}
                            value={form.password}
                            onChange={handleChange}
                            placeholder="Gib dein Passwort ein"
                        />
                        {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                    </div>

                    <button type="submit" className="btn btn-secondary w-100 mb-2">Anmelden</button>
                </form>


                <p className="text-center mt-3">
                    Hast du bereits ein account?{" "}
                    <a href="/accounts/signin" className="text-info text-decoration-none">
                        Hier anmelden
                    </a>
                </p>
            </div>
        </div>
    );
}
