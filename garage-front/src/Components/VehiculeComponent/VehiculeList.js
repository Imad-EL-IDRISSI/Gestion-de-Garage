import React, { useEffect, useState } from 'react';
import { Spinner, Alert, Button, Table } from 'react-bootstrap';
import { PencilSquare } from 'react-bootstrap-icons';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './VehiculeList.css';

const VehiculeList = ({ searchTerm, onTotalVehiculesChange }) => {
    const [vehicules, setVehicules] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchVehicules = async () => {
            try {
                const response = await axios.get('http://localhost:8888/VEHICULE-SERVICE/vehicules', {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                onTotalVehiculesChange(response.data.length);
                setVehicules(response.data);
                setLoading(false);
            } catch (err) {
                setError('Erreur lors de la récupération des véhicules');
                setLoading(false);
            }
        };

        fetchVehicules();
    }, [onTotalVehiculesChange]);

    const onEditVehicule = (vin) => {
        navigate(`/vehicules/update/${vin}`);
    };

    const filteredVehicules = vehicules.filter(vehicule =>
        vehicule.marque.toLowerCase().includes(searchTerm.toLowerCase()) ||
        vehicule.modele.toLowerCase().includes(searchTerm.toLowerCase()) ||
        vehicule.vin.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) {
        return (
            <div className="text-center w-100">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Chargement...</span>
                </Spinner>
            </div>
        );
    }

    if (error) {
        return (
            <Alert variant="danger" className="text-center w-100">
                {error}
            </Alert>
        );
    }

    return (
        <div className="w-100 overflow-auto">
            <Table bordered hover responsive>
                <thead className="table-dark">
                <tr>
                    <th className="text-truncate">#</th>
                    <th className="text-truncate">VIN</th>
                    <th className="text-truncate">Immatriculation</th>
                    <th className="text-truncate">Marque</th>
                    <th className="text-truncate">Modèle</th>
                    <th className="text-truncate">Année</th>
                    <th className="text-truncate">Couleur</th>
                    <th className="text-truncate">Kilométrage</th>
                    <th className="text-truncate">Carburant</th>
                    <th className="text-truncate">État</th>
                    <th className="text-truncate">CIN Propriétaire</th>
                    <th className="text-truncate">Nom Propriétaire</th>
                    <th className="text-truncate">Actions</th>
                </tr>
                </thead>
                <tbody>
                {filteredVehicules.map((vehicule, index) => (
                    <tr key={vehicule.vin}>
                        <td className="text-truncate">{index + 1}</td>
                        <td className="text-truncate">{vehicule.vin}</td>
                        <td className="text-truncate">{vehicule.immatriculation}</td>
                        <td className="text-truncate">{vehicule.marque}</td>
                        <td className="text-truncate">{vehicule.modele}</td>
                        <td className="text-truncate">{vehicule.annee}</td>
                        <td className="text-truncate">{vehicule.couleur}</td>
                        <td className="text-truncate">{vehicule.km} km</td>
                        <td className="text-truncate">{vehicule.typeCarb}</td>
                        <td>
                                <span
                                    className={`badge ${
                                        vehicule.etat === 'DONE'
                                            ? 'bg-success'
                                            : vehicule.etat === 'ENREPARATION'
                                                ? 'bg-warning text-dark'
                                                : vehicule.etat === 'ENATTENTE'
                                                    ? 'bg-secondary'
                                                    : 'bg-danger'
                                    }`}
                                >
                                    {vehicule.etat}
                                </span>
                        </td>
                        <td className="text-truncate">{vehicule.proprietaire?.cin || '-'}</td>
                        <td className="text-truncate">
                            {`${vehicule.proprietaire?.nom || '-'} ${vehicule.proprietaire?.prenom || '-'}`}
                        </td>
                        <td className="text-truncate">
                            <Button
                                variant="outline-primary"
                                onClick={() => onEditVehicule(vehicule.vin)}
                                size="sm"
                            >
                                <PencilSquare />
                            </Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    );
};

export default VehiculeList;