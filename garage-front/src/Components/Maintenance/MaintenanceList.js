import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Table, Button, Spinner, Alert, Badge} from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { PencilSquare } from 'react-bootstrap-icons';
import './MaintenanceList.css';

const MaintenanceList = ({ searchTerm, onTotalMaintenancesChange }) => {
    const [maintenances, setMaintenances] = useState([]);
    const [filteredMaintenances, setFilteredMaintenances] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchMaintenances = async () => {
            try {
                const response = await axios.get('http://localhost:8888/MAINTENANCE-SERVICE/maintenanceTasks');
                setMaintenances(response.data);
                onTotalMaintenancesChange(response.data.length);
            } catch (err) {
                setError('Erreur lors de la récupération des maintenances.');
            } finally {
                setLoading(false);
            }
        };
        fetchMaintenances();
    }, []);

    // Filtrage des maintenances basé sur le terme de recherche
    useEffect(() => {
        let result = maintenances;

        if (searchTerm) {
            const searchTermLower = searchTerm.toLowerCase();
            result = result.filter(maintenance =>
                // Recherche dans différents champs
                maintenance.id.toString().includes(searchTermLower) ||
                (maintenance.description || '').toLowerCase().includes(searchTermLower) ||
                (maintenance.vehicule?.vin || '').toLowerCase().includes(searchTermLower) ||
                (maintenance.statut || '').toLowerCase().includes(searchTermLower)
            );
        }

        setFilteredMaintenances(result);
        onTotalMaintenancesChange(result.length);
    }, [maintenances, searchTerm]);

    const onEditMaintenance = (id) => {
        navigate(`/maintenances/update/${id}`);
    };

    if (loading) {
        return (
            <div className="text-center my-5">
                <Spinner animation="border" variant="primary" />
            </div>
        );
    }

    if (error) {
        return <Alert variant="danger" className="text-center">{error}</Alert>;
    }

    // Utiliser filteredMaintenances ou maintenances selon qu'une recherche est active
    const displayMaintenances = searchTerm ? filteredMaintenances : maintenances;

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h5 className="text-success">
                    Total Maintenances: <Badge bg="info">{displayMaintenances.length}</Badge>
                </h5>
            </div>
            <div className="table-responsive d-none d-md-block">
                <Table striped bordered hover>
                    <thead className="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Description</th>
                        <th>Date de début</th>
                        <th>Date de fin</th>
                        <th>Statut</th>
                        <th>VIN du véhicule</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {displayMaintenances.map((maintenance) => (
                        <tr key={maintenance.id}>
                            <td>{maintenance.id}</td>
                            <td>{maintenance.description}</td>
                            <td>{new Date(maintenance.debut).toLocaleDateString()}</td>
                            <td>{new Date(maintenance.fin).toLocaleDateString()}</td>
                            <td>
                                <span
                                    className={`badge ${
                                        maintenance.statut === 'PLANNED'
                                            ? 'bg-primary'
                                            : maintenance.statut === 'IN_PROGRESS'
                                                ? 'bg-warning text-dark'
                                                : maintenance.statut === 'COMPLETED'
                                                    ? 'bg-success'
                                                    : 'bg-dark'
                                    }`}
                                >
                                    {maintenance.statut}
                                </span>
                            </td>
                            <td>{maintenance.vehicule.vin}</td>
                            <td>
                                <Button
                                    variant="outline-primary"
                                    onClick={() => onEditMaintenance(maintenance.id)}
                                >
                                    <PencilSquare /> Modifier
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
                {displayMaintenances.length === 0 && (
                    <div className="text-center text-muted mt-4">
                        Aucune maintenance trouvée.
                    </div>
                )}
            </div>

            {/* Cartes pour les petits écrans */}
            <div className="row d-md-none">
                {displayMaintenances.map((maintenance) => (
                    <div className="col-12 col-sm-6 col-md-4 mb-3" key={maintenance.id}>
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{maintenance.description}</h5>
                                <p className="card-text">
                                    <strong>Date début :</strong> {new Date(maintenance.debut).toLocaleDateString()} <br />
                                    <strong>Date fin :</strong> {new Date(maintenance.fin).toLocaleDateString()} <br />
                                    <strong>Statut :</strong>{' '}
                                    <span
                                        className={`badge ${
                                            maintenance.statut === 'IN_PROGRESS'
                                                ? 'bg-warning text-dark'
                                                : maintenance.statut === 'COMPLETED'
                                                    ? 'bg-success'
                                                    : 'bg-primary'
                                        }`}
                                    >
                                        {maintenance.statut}
                                    </span>
                                    <br />
                                    <strong>VIN :</strong> {maintenance.vehicule.vin}
                                </p>
                                <Button
                                    variant="outline-primary"
                                    onClick={() => onEditMaintenance(maintenance.id)}
                                >
                                    <PencilSquare /> Modifier
                                </Button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {displayMaintenances.length === 0 && (
                <div className="text-center text-muted d-md-none">
                    Aucune maintenance trouvée.
                </div>
            )}
        </div>
    );
};

export default MaintenanceList;