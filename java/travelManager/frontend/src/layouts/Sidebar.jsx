import { useState } from "react";
import styles from "./Sidebar.module.css";
import { removeSession } from "../lib/session";

export default function Sidebar() {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    return (
        <>
            <button className={styles.burgerMenu} onClick={toggleSidebar}>
                <img src="/assets/menu.png" alt="Menu Icon" />
            </button>

            <div className={`${styles.sidebar} ${isSidebarOpen ? styles.sidebarOpen : ""}`}>
                <div className={styles.header}>
                    <img src="/assets/tm_logo.png" alt="LEO Logo" className={styles.logo} />
                    <h4 className={styles.title}>L.E.O</h4>
                </div>

                <nav className={styles.nav}>
                    {/* Nur die Logout-Funktion bleibt erhalten */}
                    <div className={styles.footer}>
                        <hr className={styles.separator} />
                        <a
                            href="/logout"
                            onClick={(e) => {
                                e.preventDefault();
                                removeSession();
                                window.location.href = "/";
                            }}
                            className={`${styles.navLink}`}
                        >
                            <img
                                src="/assets/icons/logout.png"
                                alt="Logout Icon"
                                className={styles.icon}
                            />
                            Logout
                        </a>
                    </div>
                </nav>
            </div>

            {/* Overlay, um die Sidebar zu schließen */}
            {isSidebarOpen && (
                <div className={styles.overlay} onClick={toggleSidebar}></div>
            )}
        </>
    );
}