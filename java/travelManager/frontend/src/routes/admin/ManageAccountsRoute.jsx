import {useEffect, useState} from "react";
import {deleteUser, fetchUsers} from "@/lib/users/users";
import {useMessage} from "@/context/MessageContext";
import ConfirmationModal from "@/components/ConfirmationModal";
import {useCurrentUser} from "@/lib/session";

export default function ManageAccountsRoute() {
    const [users, setUsers] = useState([]);
    const [selectedUserId, setSelectedUserId] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const {showMessage} = useMessage();
    const currentUser = useCurrentUser();
    const [loadError, setLoadError] = useState(false);


    useEffect(() => {
        fetchUsers()
            .then(setUsers)
            .catch(() => {
                showMessage("error", "Fehler beim Laden der Benutzer.");
                setLoadError(true);
            });
    }, [showMessage]);

    const filteredUsers = users.filter((u) =>
        u.roles?.some((r) => r === "ADMIN" || r === "USER")
    );

    const confirmDelete = (userId) => {
        setSelectedUserId(userId);
        setIsModalOpen(true);
    };

    const handleDelete = async () => {
        try {
            await deleteUser(selectedUserId);
            showMessage("success", "Benutzer erfolgreich gelöscht.");
            setUsers((prev) => prev.filter((u) => u.id !== selectedUserId));
        } catch {
            showMessage("error", "Löschen fehlgeschlagen.");
        } finally {
            setIsModalOpen(false);
        }
    };

    return (
        <div className="container py-5">
            <h2 className="mb-4">Benutzerverwaltung</h2>

            {loadError && (
                <div className="alert alert-danger text-center">
                    Fehler beim Laden der Benutzer.
                </div>
            )}

            <div className="table-responsive">
                <table className="table table-bordered align-middle text-center">
                    <thead className="table-light">
                    <tr>
                        <th>Benutzername</th>
                        <th>E-Mail</th>
                        <th>Rolle(n)</th>
                        <th>Aktionen</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredUsers.map((u) => (
                        <tr key={u.id}>
                            <td>{u.username}</td>
                            <td>{u.email}</td>
                            <td>
                                {(u.roles || []).map((r) => (
                                    <span key={r} className="badge bg-secondary me-1">{r}</span>
                                ))}
                            </td>
                            <td>
                                {u.id !== currentUser?.id ? (
                                    <button
                                        className="btn btn-outline-danger btn-sm"
                                        onClick={() => confirmDelete(u.id)}
                                    >
                                        Löschen
                                    </button>
                                ) : (
                                    <span className="text-muted small">eigener Account</span>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            {isModalOpen && (
                <ConfirmationModal
                    title="Benutzer löschen"
                    message="Möchten Sie diesen Benutzer wirklich löschen?"
                    onConfirm={handleDelete}
                    onCancel={() => setIsModalOpen(false)}
                />
            )}
        </div>
    );
}
