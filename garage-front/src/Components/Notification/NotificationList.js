import React, { useEffect, useState } from 'react';
import { Table, Button, Spinner, Alert, Card } from 'react-bootstrap';
import { Envelope, CheckCircle, XCircle } from 'react-bootstrap-icons';
import axios from 'axios';

const NotificationList = ({ searchTerm, onTotalNotificationsChange }) => {
    const [notifications, setNotifications] = useState([]);
    const [filteredNotifications, setFilteredNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Récupère les notifications lors du chargement initial
    useEffect(() => {
        const fetchNotifications = async () => {
            try {
                const response = await axios.get('http://localhost:8888/NOTIFICATION-SERVICE/notification');
                setNotifications(response.data);
                onTotalNotificationsChange(response.data.length);
            } catch (err) {
                setError('Erreur lors de la récupération des notifications.');
            } finally {
                setLoading(false);
            }
        };
        fetchNotifications();
    }, []);

    // Filtrage des notifications basé sur le terme de recherche
    useEffect(() => {
        let result = notifications;

        if (searchTerm) {
            const searchTermLower = searchTerm.toLowerCase();
            result = result.filter(notification =>
                // Recherche dans différents champs
                notification.id.toString().includes(searchTermLower) ||
                (notification.maintenance?.vehicule?.vin || '').toLowerCase().includes(searchTermLower) ||
                (notification.maintenance?.vehicule?.marque || '').toLowerCase().includes(searchTermLower) ||
                (notification.maintenance?.vehicule?.proprietaire?.nom || '').toLowerCase().includes(searchTermLower) ||
                (notification.maintenance?.vehicule?.proprietaire?.prenom || '').toLowerCase().includes(searchTermLower)
            );
        }

        setFilteredNotifications(result);
        onTotalNotificationsChange(result.length);
    }, [notifications, searchTerm]);

    // Envoie la notification et met à jour l'état de 'isSent'
    const onSendNotification = async (id) => {
        try {
            const response = await axios.post(`http://localhost:8888/NOTIFICATION-SERVICE/notification/send/${id}`);

            if (response.data) {
                setNotifications(prevNotifications =>
                    prevNotifications.map(notification =>
                        notification.id === id
                            ? {
                                ...notification,
                                isSent: true,
                                dateEnvoi: new Date(response.data.dateEnvoi).toLocaleString('fr-FR', {
                                    weekday: 'short',
                                    year: 'numeric',
                                    month: 'short',
                                    day: 'numeric',
                                    hour: '2-digit',
                                    minute: '2-digit',
                                })
                            }
                            : notification
                    )
                );
            }
        } catch (err) {
            setError('Erreur lors de l\'envoi de la notification.');
        }
    };

    if (loading) return <Spinner animation="border" variant="primary" />;
    if (error) return <Alert variant="danger">{error}</Alert>;

    // Utiliser filteredNotifications au lieu de notifications
    const displayNotifications = searchTerm ? filteredNotifications : notifications;

    return (
        <div className="container mt-5">
            <Card className="mb-4 shadow-lg">
                <Card.Body>
                    <Table className="table-custom table-striped table-hover table-bordered">
                        <thead className="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>VIN du Véhicule</th>
                            <th>Marque</th>
                            <th>Nom du Propriétaire</th>
                            <th>Date d'envoi</th>
                            <th>État</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {displayNotifications.map((notification) => (
                            <tr key={notification.id}>
                                <td>{notification.id}</td>
                                <td>{notification.maintenance?.vehicule?.vin || 'N/A'}</td>
                                <td>{notification.maintenance?.vehicule?.marque || 'N/A'}</td>
                                <td>{notification.maintenance?.vehicule?.proprietaire?.nom + " " + notification.maintenance?.vehicule?.proprietaire?.prenom || 'N/A'}</td>
                                <td>{notification.isSent ? notification.dateEnvoi : 'N/A'}</td>

                                <td>
                                    {notification.isSent ? (
                                        <span className="badge bg-success text-white d-flex align-items-center">
                                                <CheckCircle size={20} className="me-2" /> Envoyé
                                            </span>
                                    ) : (
                                        <span className="badge bg-danger text-white d-flex align-items-center">
                                                <XCircle size={20} className="me-2" /> Non envoyé
                                            </span>
                                    )}
                                </td>
                                <td>
                                    {!notification.isSent ? (
                                        <Button
                                            variant="outline-success"
                                            onClick={() => onSendNotification(notification.id)}
                                            className="d-flex align-items-center"
                                        >
                                            <Envelope size={20} className="me-2" />
                                        </Button>
                                    ) : (
                                        <Button variant="secondary" disabled>
                                            <CheckCircle size={20} className="me-2" />
                                        </Button>
                                    )}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    {displayNotifications.length === 0 && (
                        <div className="text-center text-muted">
                            Aucune notification trouvée.
                        </div>
                    )}
                </Card.Body>
            </Card>
        </div>
    );
};

export default NotificationList;