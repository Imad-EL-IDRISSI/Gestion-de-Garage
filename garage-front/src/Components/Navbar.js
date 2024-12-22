// AppNavbar.js
import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';

const AppNavbar = () => {
  return (
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="/">My App</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="/clients">Clients</Nav.Link>
              <Nav.Link href="/vehicules">Vehicules</Nav.Link>
              <Nav.Link href="/maintenances">Maintenance</Nav.Link>
              <Nav.Link href="/notification">Notification</Nav.Link>
              <Nav.Link href="/factures">Factures</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
  );
};

export default AppNavbar;
