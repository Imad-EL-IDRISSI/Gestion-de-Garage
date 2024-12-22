import React, { useState } from 'react';
import { Container, Row, Col, Button, Card, Form, InputGroup, Badge, Pagination } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FaPlus, FaSearch, FaUser, FaFilter } from 'react-icons/fa';
import ClientList from "../Components/Client/ClientList";

function Client() {
    const [searchTerm, setSearchTerm] = useState('');
    const [totalClients, setTotalClients] = useState(0); // State to store the total clients

    const navigate = useNavigate();

    const handleAddClientClick = () => {
        navigate('/add-client');
    };

    const handleTotalClientsChange = (count) => {
        setTotalClients(count);
    };
    return (
        <Container className="mt-5">
            <Row className="justify-content-center">
                <Col md={12} lg={10}>
                    {/* Section Titre */}
                    <div className="text-center mb-4">
                        <h1 className="display-4 text-primary">Client Management</h1>
                        <p className="lead text-muted">Manage all your clients efficiently in one place.</p>
                    </div>

                    {/* Résumé et Actions */}
                    <Row className="mb-4 align-items-center">
                    <Col>
                            <h5 className="text-success">
                                <FaUser className="me-2" />
                                Total Clients: <Badge bg="info">{totalClients}</Badge> {/* Display total clients */}
                            </h5>
                        </Col>
                        
                    </Row>

                    {/* Barre de recherche et filtres */}
                    <Card className="shadow-sm border-0 rounded mb-4">
                        <Card.Body className="p-4">
                            <Row className="align-items-center">
                                <Col md={8}>
                                    <InputGroup>
                                        <InputGroup.Text className="bg-light border-0">
                                            <FaSearch />
                                        </InputGroup.Text>
                                        <Form.Control
                                            type="text"
                                            placeholder="Search clients..."
                                            value={searchTerm}
                                            onChange={(e) => setSearchTerm(e.target.value)}
                                            className="border-0 shadow-sm"
                                        />
                                    </InputGroup>
                                </Col>
                                <Col className="text-end">
                            <Button
                                variant="primary"
                                size="lg"
                                onClick={handleAddClientClick}
                                className="d-flex align-items-center shadow"
                            >
                                <FaPlus className="me-2" /> Add New Client
                            </Button>
                        </Col>
                            </Row>
                        </Card.Body>
                    </Card>

                    {/* Liste des clients */}
                    <Card className="shadow-lg border-0 rounded">
                        <Card.Header className="bg-primary text-white text-center">
                            <h5 className="mb-0">Client List</h5>
                        </Card.Header>
                        <Card.Body>
                            <ClientList searchTerm={searchTerm} onTotalClientsChange={handleTotalClientsChange} /> {/* Pass the handler */}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default Client;
