import React, { useState } from 'react';
import { Container, Row, Col, Button, Card, Form, InputGroup, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { FaPlus, FaSearch } from 'react-icons/fa';
import NotificationList from '../Components/Notification/NotificationList';

function Notification() {
    const [searchTerm, setSearchTerm] = useState('');
    const [totalNotifications, setTotalNotifications] = useState(0); // State to store the total notifications

    const navigate = useNavigate();

    const handleAddNotificationClick = () => {
        navigate('/notification/new');
    };

    const handleTotalNotificationsChange = (count) => {
        setTotalNotifications(count);
    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-center">
                <Col md={12} lg={10}>
                    {/* Section Title */}
                    <div className="text-center mb-4">
                        <h1 className="display-4 text-primary">Notification Management</h1>
                        <p className="lead text-muted">Manage all your notifications efficiently in one place.</p>
                    </div>

                    {/* Summary and Actions */}
                    <Row className="mb-4 align-items-center">
                        <Col>
                            <h5 className="text-success">
                                Total Notifications: <Badge bg="info">{totalNotifications}</Badge> {/* Display total notifications */}
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
                                            placeholder="Search notifications..."
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
                                        onClick={handleAddNotificationClick}
                                        className="d-flex align-items-center shadow"
                                    >
                                        <FaPlus className="me-2" /> Add New Notification
                                    </Button>
                                </Col>
                            </Row>
                        </Card.Body>
                    </Card>

                    {/* Notification List */}
                    <Card className="shadow-lg border-0 rounded">
                        <Card.Header className="bg-primary text-white text-center">
                            <h5 className="mb-0">Notification List</h5>
                        </Card.Header>
                        <Card.Body>
                            <NotificationList searchTerm={searchTerm} onTotalNotificationsChange={handleTotalNotificationsChange} /> {/* Pass the handler */}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}

export default Notification;
