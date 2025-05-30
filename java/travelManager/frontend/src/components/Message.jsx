import {useEffect, useState} from "react";
import styles from "./Message.module.css";

export default function Message({type = "success", message, onClose, className = ""}) {

    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        if (message) {
            setIsVisible(true);
            const timer = setTimeout(() => {
                setIsVisible(false);
                if (onClose) {
                    onClose();
                }
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [message, onClose]);

    if (!isVisible) {
        return null;
    }

    return (
        <div className={`${styles.message} ${styles[type]} ${className}`}>
            {message}
        </div>
    );
}