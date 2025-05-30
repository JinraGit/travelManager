import {useNavigate} from "react-router";
import styles from "./NotFoundRoute.module.css";
import notFound from "/assets/notFound.png";

export default function NotFoundRoute() {
    const navigate = useNavigate();

    return (
        <div className={styles.container}>
            <img src={notFound} alt="Not Found" className={styles.image}/>
            <h1 className={styles.title}>Wir konnten diese Seite nicht finden.</h1>
            <p className={styles.message}>
                Vielleicht hast du dich vertippt oder der Link ist veraltet.
            </p>
            <button className={styles.button} onClick={() => navigate("/")}>
                Zurück zur Startseite
            </button>
        </div>
    );
}