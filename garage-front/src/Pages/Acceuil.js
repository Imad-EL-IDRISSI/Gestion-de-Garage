import React from 'react';
import { Button, Container, Row, Col } from 'react-bootstrap';
import './Acceuil.css';  // Assurez-vous d'inclure les styles CSS dans un fichier séparé

const HomePage = () => {
    return (
        <div className="home-container">
            <Container className="text-center text-white d-flex align-items-center justify-content-center min-vh-100">
                <Row>
                    <Col md={12} className="position-relative">
                        <div className="overlay">
                            <h1 className="display-4 font-weight-bold">
                                Optimisez la gestion de vos services automobiles
                            </h1>
                            <p className="lead mb-4">
                                Notre plateforme vous permet de gérer vos clients, véhicules, maintenances et notifications en un seul endroit, pour une expérience fluide et efficace.
                            </p>

                            <div className="button-group">
                                <Button variant="primary" size="lg" className="cta-btn" href="/clients">
                                    Gérer les Clients
                                </Button>
                                <Button variant="primary" size="lg" className="cta-btn" href="/vehicules">
                                    Gérer les Véhicules
                                </Button>
                                <Button variant="primary" size="lg" className="cta-btn" href="/maintenances">
                                    Gérer les Maintenances
                                </Button>
                                <Button variant="primary" size="lg" className="cta-btn" href="/notification">
                                    Gérer les Notifications
                                </Button>
                            </div>
                        </div>
                    </Col>
                </Row>
            </Container>
        </div>
    );
};

export default HomePage;
