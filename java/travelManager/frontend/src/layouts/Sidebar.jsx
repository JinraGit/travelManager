import { useState } from "react";
import { useLocation } from "react-router";
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

    // const dashboardPath = isAdmin ? "/dashboard/admin" : isUser ? "/dashboard/user" : "/";

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
                    <img src="/assets/tm_logo.png" alt="LEO Logo" className={styles.logo} />
                    <h4 className={styles.title}>L.E.O</h4>
                </div>

                {/* Navigation */}
                <nav className={styles.nav}>
                    {/* Dashboard Link */}
                    {/*<a href={dashboardPath} className={`${styles.navLink} ${isActive(dashboardPath)}`}>*/}
                    {/*    <img src="/assets/icons/home.png" alt="Dashboard Icon" className={styles.icon} />*/}
                    {/*    Dashboard*/}
                    {/*</a>*/}

                    <hr className={styles.separator} />

                    {/* Admin-only Links */}
                    {isAdmin && (
                        <>
                            <a href="/admin/manage-accounts" className={`${styles.navLink} ${isActive("/admin/manage-accounts")}`}>
                                <img src="/assets/icons/group.png" alt="Benutzerverwaltung Icon" className={styles.icon} />
                                Benutzerverwaltung
                            </a>
                        </>
                    )}

                    {/* User-only Links */}
                    {isUser && (
                        <>
                            {/*<a href="/user/profile" className={`${styles.navLink} ${isActive("/user/profile")}`}>*/}
                            {/*    <img src="/assets/icons/user.png" alt="Benutzerprofil Icon" className={styles.icon} />*/}
                            {/*    Mein Profil*/}
                            {/*</a>*/}
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