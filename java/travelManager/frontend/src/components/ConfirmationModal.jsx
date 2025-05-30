import styles from "./ConfirmationModal.module.css";

export default function ConfirmationModal({message, onConfirm, onCancel}) {
    return (
        <div className={styles.modalOverlay}>
            <div className={styles.modal}>
                <p className={styles.message}>{message}</p>
                <div className={styles.buttonGroup}>
                    <button className={styles.confirmButton} onClick={onConfirm}>
                        Ja
                    </button>
                    <button className={styles.cancelButton} onClick={onCancel}>
                        Nein
                    </button>
                </div>
            </div>
        </div>
    );
}