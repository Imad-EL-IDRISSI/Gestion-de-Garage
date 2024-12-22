import React, { useState } from 'react';
import { Container, Row, Col, Button, Card, Form, InputGroup, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FaPlus, FaSearch } from 'react-icons/fa';
import MaintenanceList from '../Components/Maintenance/MaintenanceList';

function Maintenance() {
    const [searchTerm, setSearchTerm] = useState('');
    const [totalMaintenances, setTotalMaintenances] = useState(0); // State to store the total maintenances

    const navigate = useNavigate();

    const handleAddMaintenanceClick = () => {
        navigate('/maintenances/new');
    };

    const handleTotalMaintenancesChange = (count) => {
        setTotalMaintenances(count);
    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-center">
                <Col md={12} lg={10}>
                    {/* Section Title */}
                    <div className="text-center mb-4">
                        <h1 className="display-4 text-primary">Maintenance Management</h1>
                        <p className="lead text-muted">Manage all your maintenance records efficiently in one place.</p>
                    </div>

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
                                            placeholder="Search maintenances..."
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
                                        onClick={handleAddMaintenanceClick}
                                        className="d-flex align-items-center shadow"
                                    >
                                        <FaPlus className="me-2" /> Add New Maintenance
                                    </Button>
                                </Col>
                            </Row>
                        </Card.Body>
                    </Card>

                    {/* Maintenance List */}
                    <Card className="shadow-lg border-0 rounded">
                        <Card.Header className="bg-primary text-white text-center">
                            <h5 className="mb-0">Maintenance List</h5>
                        </Card.Header>
                        <Card.Body>
                            <MaintenanceList searchTerm={searchTerm} onTotalMaintenancesChange={handleTotalMaintenancesChange} /> {/* Pass the handler */}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default Maintenance;
