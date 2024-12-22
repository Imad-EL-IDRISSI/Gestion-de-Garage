import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Alert, Spinner, Container, Row, Col } from 'react-bootstrap';
import './MaintenanceUpdate.css';

const MaintenanceUpdate = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [maintenance, setMaintenance] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        const fetchMaintenance = async () => {
            try {
                const response = await axios.get(`http://localhost:8888/MAINTENANCE-SERVICE/maintenanceTasks/${id}`);
                // Assurez-vous que les dates sont dans le format attendu par le champ input
                const data = response.data;
                setMaintenance({
                    ...data,
                    debut: formatDate(data.debut),
                    fin: formatDate(data.fin)
                });
            } catch (err) {
                setError('Erreur lors de la récupération des données de maintenance.');
            }
        };
        fetchMaintenance();
    }, [id]);

    const formatDate = (date) => {
        if (date) {
            const d = new Date(date);
            return d.toISOString().split('T')[0];  // Format YYYY-MM-DD
        }
        return '';
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            await axios.patch(`http://localhost:8888/MAINTENANCE-SERVICE/maintenanceTasks/update/${id}`, maintenance);
            setSuccess(true);
            setTimeout(() => navigate('/maintenances'), 2000);
        } catch (err) {
            setError("Erreur lors de la mise à jour de la maintenance.");
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setMaintenance((prev) => ({ ...prev, [name]: value }));
    };

    if (error) {
        return <Alert variant="danger" className="text-center">{error}</Alert>;
    }

    if (!maintenance) {
        return (
            <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
                <Spinner animation="border" variant="primary" />
            </div>
        );
    }

    return (
        <Container className="form-container">
            <Row className="text-center mb-4">
                <Col>
                    <h2>Modifier la Maintenance</h2>
                    <p>ID Maintenance : <strong>{id}</strong></p>
                    {success && <Alert variant="success">Maintenance mise à jour avec succès !</Alert>}
                </Col>
            </Row>
            <Form onSubmit={handleUpdate}>
                <Form.Group className="mb-3">
                    <Form.Label>Date de début</Form.Label>
                    <Form.Control
                        type="date"
                        name="debut"
                        value={maintenance.debut || ''}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Date de fin</Form.Label>
                    <Form.Control
                        type="date"
                        name="fin"
                        value={maintenance.fin || ''}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        name="description"
                        value={maintenance.description || ''}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Statut</Form.Label>
                    <Form.Select
                        name="statut"
                        value={maintenance.statut || ''}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Sélectionnez le statut</option>
                        <option value="PLANNED">PLANNED</option>
                        <option value="IN_PROGRESS">IN_PROGRESS</option>
                        <option value="COMPLETED">COMPLETED</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>ID Véhicule</Form.Label>
                    <Form.Control
                        type="text"
                        name="vehiculeId"
                        value={maintenance.vehiculeId || ''}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Button type="submit" variant="primary" className="w-100">Mettre à jour</Button>
            </Form>
        </Container>
    );
};

export default MaintenanceUpdate;
