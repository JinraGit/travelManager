import { useState } from "react";
import { useLocation } from "react-router";
import { Link } from "react-router-dom";
import styles from "./Sidebar.module.css";
import { removeSession, getUserRoles } from "../lib/session";

export default function Sidebar() {
    const location = useLocation();
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const session = JSON.parse(localStorage.getItem("session") || "{}");
    const accountName = session?.account?.username ?? "";

    const roles = getUserRoles();
    const isAdmin = roles.includes("ADMIN");
    const isUser = roles.includes("USER");

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    const isActive = (path) => (location.pathname === path ? styles.active : "");

    const roleLabel = isAdmin ? "Admin" : isUser ? "Benutzer" : "Gast";

    return (
        <>
            {/* Burger Button */}
            <button className={styles.burgerMenu} onClick={toggleSidebar}>
                <img src="/assets/menu.png" alt="Menu Icon" />
            </button>

            {/* Sidebar */}
            <div className={`${styles.sidebar} ${isSidebarOpen ? styles.sidebarOpen : ""}`}>
                {/* Header */}
                <div className={styles.header}>
                    <img src="/assets/tm_logo.png" alt="TravelManager Logo" className={styles.logo} />
                    <h4 className={styles.title}>Travel Manager</h4>
                </div>

                {/* Navigation */}
                <nav className={styles.nav}>
                    <hr className={styles.separator} />

                    {/* Admin-only Links */}
                    {isAdmin && (
                        <>
                            <Link to="/admin/manage-accounts" className={`${styles.navLink} ${isActive("/admin/manage-accounts")}`}>
                                <img src="/assets/icons/group.png" alt="Benutzerverwaltung Icon" className={styles.icon} />
                                Benutzerverwaltung
                            </Link>
                            <Link to="/admin/create-admin" className={`${styles.navLink} ${isActive("/admin/create-admin")}`}>
                                <img src="/assets/icons/administrate.png" alt="Admin erstellen Icon" className={styles.icon} />
                                Admin erstellen
                            </Link>
                        </>
                    )}

                    {/* User-only Links */}
                    {isUser && (
                        <>
                            <Link to="/trips/create" className={`${styles.navLink} ${isActive("/trips/create")}`}>
                                <img src="/assets/icons/newTrip.png" alt="Neuen Trip erstellen" className={styles.icon} />
                                Trip eintragen
                            </Link>
                            <Link to="/trips/all" className={`${styles.navLink} ${isActive("/trips/all")}`}>
                                <img src="/assets/icons/allTrips.png" alt="Alle Trips anzeigen" className={styles.icon} />
                                Trips anzeigen
                            </Link>
                            <Link to="/meetings/create" className={`${styles.navLink} ${isActive("/meetings/create")}`}>
                                <img src="/assets/icons/meetingCreate.png" alt="Neues Meeting erstellen" className={styles.icon} />
                                Meeting eintragen
                            </Link>
                            <Link to="/meetings/all" className={`${styles.navLink} ${isActive("/meetings/all")}`}>
                                <img src="/assets/icons/meeting.png" alt="Alle Meetings anzeigen" className={styles.icon} />
                                Meetings anzeigen
                            </Link>
                        </>
                    )}
                </nav>

                {/* Footer mit Logout */}
                <div className={styles.footer}>
                    <hr className={styles.separator} />
                    <span className={styles.roleIndicator}>Eingeloggt als: {roleLabel}</span>
                    {accountName && <div className="text-muted small">{accountName}</div>}

                    <a
                        href="/logout"
                        onClick={(e) => {
                            e.preventDefault();
                            removeSession();
                            window.location.href = "/";
                        }}
                        className={`${styles.navLink}`}
                    >
                        <img src="/assets/icons/logout.png" alt="Logout Icon" className={styles.icon} />
                        Logout
                    </a>
                </div>
            </div>

            {/* Overlay zum Schliessen der Sidebar */}
            {isSidebarOpen && <div className={styles.overlay} onClick={toggleSidebar}></div>}
        </>
    );
}
