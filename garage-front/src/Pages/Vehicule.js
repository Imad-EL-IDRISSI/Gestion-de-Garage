import React, { useState } from 'react';
import { Row, Col, Button, Card, InputGroup, Form, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import VehiculeList from '../Components/VehiculeComponent/VehiculeList';
import { FaCar, FaPlus, FaSearch } from 'react-icons/fa';

function Vehicule() {
    const [searchTerm, setSearchTerm] = useState('');
    const [totalVehicules, setTotalVehicule] = useState(0);
    const navigate = useNavigate();

    const handleAddVehiculeClick = () => {
        navigate('/add-vehicule');
    };

    const handletotalVehiculesChange = (count) => {
        setTotalVehicule(count);
    };

    return (
        <div className="p-4">
            <div className="mx-auto" style={{ maxWidth: '1400px' }}>
                {/* Section Titre */}
                <div className="text-center mb-4">
                    <h1 className="display-4 text-primary">Gestion des Véhicules</h1>
                    <p className="lead text-muted">Gérez tous vos véhicules efficacement.</p>
                </div>

                <Row className="mb-4 align-items-center">
                    <Col>
                        <h5 className="text-success">
                            <FaCar className="me-2" />
                            Total Vehicule: <Badge bg="info">{totalVehicules}</Badge>
                        </h5>
                    </Col>
                </Row>

                {/* Action et Barre de Recherche */}
                <Row className="mb-4 align-items-center">
                    <Col md={8}>
                        <InputGroup>
                            <InputGroup.Text className="bg-light border-0">
                                <FaSearch />
                            </InputGroup.Text>
                            <Form.Control
                                type="text"
                                placeholder="Rechercher un véhicule..."
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                className="border-0 shadow-sm"
                            />
                        </InputGroup>
                    </Col>
                    <Col md={4} className="text-end">
                        <Button
                            variant="primary"
                            size="lg"
                            onClick={handleAddVehiculeClick}
                            className="shadow"
                        >
                            <FaPlus className="me-2" /> Ajouter un Nouveau Véhicule
                        </Button>
                    </Col>
                </Row>

                {/* Liste des véhicules */}
                <Card className="shadow-lg border-0 rounded">
                    <Card.Header className="bg-primary text-white text-center">
                        <h5 className="mb-0">Liste des Véhicules</h5>
                    </Card.Header>
                    <Card.Body className="p-0">
                        <VehiculeList
                            searchTerm={searchTerm}
                            onTotalVehiculesChange={handletotalVehiculesChange}
                        />
                    </Card.Body>
                </Card>
            </div>
        </div>
    );
}

export default Vehicule;