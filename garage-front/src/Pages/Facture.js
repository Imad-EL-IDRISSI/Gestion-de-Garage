import React, { useState } from 'react';
import { Container, Row, Col, Button, Card, Form, InputGroup, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FaPlus, FaSearch } from 'react-icons/fa';
import FactureList from '../Components/Facture/FactureList';

function Facture() {
    const [searchTerm, setSearchTerm] = useState('');
    const [totalFactures, setTotalFactures] = useState(0); // State to store the total invoices

    const navigate = useNavigate();

    const handleAddFactureClick = () => {
        navigate('/factures/new');
    };

    const handleTotalFacturesChange = (count) => {
        setTotalFactures(count);
    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-center">
                <Col md={12} lg={10}>
                    {/* Section Title */}
                    <div className="text-center mb-4">
                        <h1 className="display-4 text-primary">Facture Management</h1>
                        <p className="lead text-muted">Manage all your invoices efficiently in one place.</p>
                    </div>

                    {/* Summary and Actions */}
                    <Row className="mb-4 align-items-center">
                        <Col>
                            <h5 className="text-success">
                                Total Invoices: <Badge bg="info">{totalFactures}</Badge> {/* Display total invoices */}
                            </h5>
                        </Col>
                    </Row>

                    {/* Search Bar and Add Button */}
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
                                            placeholder="Search invoices..."
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
                                        onClick={handleAddFactureClick}
                                        className="d-flex align-items-center shadow"
                                    >
                                        <FaPlus className="me-2" /> Add New Invoice
                                    </Button>
                                </Col>
                            </Row>
                        </Card.Body>
                    </Card>

                    {/* Facture List */}
                    <Card className="shadow-lg border-0 rounded">
                        <Card.Header className="bg-primary text-white text-center">
                            <h5 className="mb-0">Invoice List</h5>
                        </Card.Header>
                        <Card.Body>
                            <FactureList searchTerm={searchTerm} onTotalFacturesChange={handleTotalFacturesChange} /> {/* Pass the handler */}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default Facture;
