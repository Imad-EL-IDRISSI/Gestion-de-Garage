import React, { useState, useEffect } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { FaInfoCircle } from 'react-icons/fa';
import './NotificationForm.css';

const NotificationForm = () => {
    const [status, setStatus] = useState('NONENVOYE'); // Default status
    const [idMaintenance, setIdMaintenance] = useState('');
    const [maintenances, setMaintenances] = useState([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    // Récupère les maintenances avec état PLANNED
    useEffect(() => {
        const fetchMaintenances = async () => {
            try {
                const response = await fetch('http://localhost:8888/MAINTENANCE-SERVICE/maintenanceTasks/maintenances/planned');
                const data = await response.json();
                console.log("+++++++++++++++++++",data);
                setMaintenances(data);
            } catch (err) {
                setError('Erreur lors de la récupération des maintenances.');
            }
        };
        fetchMaintenances();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!status || !idMaintenance) {
            setError('Tous les champs sont requis.');
            return;
        }
        setError('');

        const notificationData = {
            status,
            id_Maintenance: idMaintenance,
        };

        try {
            const response = await fetch('http://localhost:8888/NOTIFICATION-SERVICE/notification/ajouter', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(notificationData),
            });

            if (!response.ok) throw new Error('Erreur lors de l\'enregistrement de la notification.');

            setSuccess('Notification ajoutée avec succès!');
            setStatus('PENDING');
            setIdMaintenance('');
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container className="form-container mt-5">
            <h2 className="text-center mb-3">Ajouter une Notification</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}
            <Form onSubmit={handleSubmit} className="shadow p-4 rounded">
                <Form.Group className="mb-3">
                    <Form.Label>Statut <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Select
                        value={status}
                        onChange={(e) => setStatus(e.target.value)}
                        required
                    >
                        <option value="ENVOYE">ENVOYE</option>
                        <option value="NONENVOYE">NONENVOYE</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>ID de Maintenance <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Select
                        value={idMaintenance}
                        onChange={(e) => setIdMaintenance(e.target.value)}
                        required
                    >
                        <option value="">Sélectionner une maintenance</option>
                        {maintenances.map((maintenance) => (
                            <option key={maintenance.id} value={maintenance.id}>
                                {maintenance.id} - {maintenance.vehiculeId || 'N/A'}
                            </option>
                        ))}
                    </Form.Select>
                </Form.Group>
                <Button variant="primary" className="w-100" type="submit">Ajouter</Button>
            </Form>
        </Container>
    );
};

export default NotificationForm;
