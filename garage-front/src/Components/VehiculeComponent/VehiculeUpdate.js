import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Alert, Spinner, Container, Row, Col } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './VehiculeUpdate.css'; // Custom CSS for additional styles

const VehiculeUpdate = () => {
    const { vin } = useParams();
    const navigate = useNavigate();
    const [vehicule, setVehicule] = useState(null);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        if (vin) {
            const fetchVehicule = async () => {
                try {
                    const response = await axios.get(`http://localhost:8888/VEHICULE-SERVICE/vehicules/${vin}`);
                    setVehicule(response.data);
                } catch (err) {
                    setError('Erreur lors de la récupération du véhicule');
                }
            };

            fetchVehicule();
        }
    }, [vin]);

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            await axios.patch(`http://localhost:8888/VEHICULE-SERVICE/vehicules/update/${vin}`, vehicule);
            setSuccess(true);
            setTimeout(() => navigate('/vehicules'), 2000);
        } catch (err) {
            setError('Erreur lors de la mise à jour du véhicule');
        }
    };

    const handleChange = (e) => {
        setVehicule({ ...vehicule, [e.target.name]: e.target.value });
    };

    if (error) {
        return <Alert variant="danger">{error}</Alert>;
    }

    if (!vehicule) {
        return <Spinner animation="border" />; // Indicate that the vehicle is loading
    }

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh', backgroundColor: '#f7f9fc' }}>
            <div style={{ maxWidth: '600px', width: '100%' }}>
                <Row className="mb-4">
                    <Col className="text-center">
                        <h2 className="text-primary" style={{ fontWeight: 'bold', fontSize: '2.5rem' }}>Modifier le Véhicule</h2>
                        <p className="text-muted" style={{ fontSize: '1.1rem' }}>Immatriculation: <strong>{vehicule.immatriculation || 'Non disponible'}</strong></p>
                        {success && <Alert variant="success">Véhicule mis à jour avec succès !</Alert>}
                    </Col>
                </Row>
                <Form onSubmit={handleUpdate}>
                    <Form.Group controlId="immatriculation" className="mb-3">
                        <Form.Label>Immatriculation</Form.Label>
                        <Form.Control
                            type="text"
                            name="immatriculation"
                            value={vehicule.immatriculation || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="marque" className="mb-3">
                        <Form.Label>Marque</Form.Label>
                        <Form.Control
                            type="text"
                            name="marque"
                            value={vehicule.marque || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="modele" className="mb-3">
                        <Form.Label>Modèle</Form.Label>
                        <Form.Control
                            type="text"
                            name="modele"
                            value={vehicule.modele || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="annee" className="mb-3">
                        <Form.Label>Année</Form.Label>
                        <Form.Control
                            type="number"
                            name="annee"
                            value={vehicule.annee || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="couleur" className="mb-3">
                        <Form.Label>Couleur</Form.Label>
                        <Form.Control
                            type="text"
                            name="couleur"
                            value={vehicule.couleur || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="km" className="mb-3">
                        <Form.Label>Kilométrage</Form.Label>
                        <Form.Control
                            type="number"
                            name="km"
                            value={vehicule.km || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="typeCarb" className="mb-3">
                        <Form.Label>Type de Carburant</Form.Label>
                        <Form.Control
                            type="text"
                            name="typeCarb"
                            value={vehicule.typeCarb || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        />
                    </Form.Group>
                    <Form.Group controlId="etat" className="mb-3">
                        <Form.Label>État</Form.Label>
                        <Form.Control
                            as="select" // Change this to "select"
                            name="etat"
                            value={vehicule.etat || ''}
                            onChange={handleChange}
                            required
                            className="rounded-pill"
                        >
                            <option value="">Sélectionnez l'état</option>
                            <option value="ENATTENTE">ENATTENTE</option>
                            <option value="ENREPARATION">ENREPARATION</option>
                            <option value="DONE">DONE</option>
                        </Form.Control>
                    </Form.Group>


                    <Button variant="primary" type="submit" className="w-100 rounded-pill">
                        Mettre à jour
                    </Button>
                </Form>
            </div>
        </Container>
    );
};

export default VehiculeUpdate;
