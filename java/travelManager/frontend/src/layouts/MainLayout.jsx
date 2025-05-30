import {Outlet, useLocation} from 'react-router-dom';
import {useTheme} from '@/context/ThemeContext';
import styles from './MainLayout.module.css';
import Sidebar from "@/layouts/Sidebar.jsx";

import Message from "@/components/Message";
import {useMessage} from "@/context/MessageContext";

export default function MainLayout() {
    const {isDarkMode} = useTheme();
    const location = useLocation();

    const {message, messageType} = useMessage();


    const hideSidebarRoutes = ['/accounts/signin', '/accounts/signup'];
    const shouldHideSidebar = hideSidebarRoutes.includes(location.pathname);

    return (
        <div className={`${styles.appWrapper} ${isDarkMode ? styles.darkMode : ''}`}>
            {!shouldHideSidebar && <Sidebar/>}
            <main className={styles.mainContent}>
                <Outlet/>
            </main>
            {message && <Message type={messageType} message={message}/>}

        </div>
    );
}