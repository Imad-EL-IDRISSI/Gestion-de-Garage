import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Alert, Spinner, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';
import { FaInfoCircle, FaCalendarAlt } from 'react-icons/fa';

const NotificationUpdate = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [notification, setNotification] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        const fetchNotification = async () => {
            try {
                const response = await axios.get(`http://localhost:8888/NOTIFICATION-SERVICE/notification/${id}`);
                setNotification(response.data);
            } catch (err) {
                setError('Erreur lors de la récupération des données de notification.');
            }
        };
        fetchNotification();
    }, [id]);

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            await axios.patch(`http://localhost:8888/NOTIFICATION-SERVICE/notification/${id}`, notification);
            setSuccess(true);
            setTimeout(() => navigate('/notifications'), 2000);
        } catch (err) {
            setError('Erreur lors de la mise à jour de la notification.');
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNotification((prev) => ({ ...prev, [name]: value }));
    };

    if (error) {
        return <Alert variant="danger">{error}</Alert>;
    }

    if (!notification) {
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
                    <h2>Modifier la Notification</h2>
                    {success && <Alert variant="success">Notification mise à jour avec succès!</Alert>}
                </Col>
            </Row>
            <Form onSubmit={handleUpdate}>
                <Form.Group className="mb-3">
                    <Form.Label>Titre <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        type="text"
                        name="title"
                        value={notification.title}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Message <FaInfoCircle className="text-muted" /></Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        name="message"
                        value={notification.message}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Date <FaCalendarAlt className="text-muted" /></Form.Label>
                    <Form.Control
                        type="date"
                        name="date"
                        value={notification.date}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Button type="submit" variant="primary" className="w-100">Mettre à jour</Button>
            </Form>
        </Container>
    );
};

export default NotificationUpdate;
