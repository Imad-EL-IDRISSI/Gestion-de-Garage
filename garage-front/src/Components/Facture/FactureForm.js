import React, { useState, useEffect } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { FaInfoCircle } from 'react-icons/fa';

const FactureForm = () => {
    const [montantTotal, setMontantTotal] = useState('');
    const [idMaintenance, setIdMaintenance] = useState('');
    const [intervention, setIntervention] = useState('');
    const [etat, setEtat] = useState(false);
    const [maintenances, setMaintenances] = useState([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    // Récupérer les maintenances dont l'état est "COMPLETED"
    useEffect(() => {
        const fetchMaintenances = async () => {
            try {
                const response = await fetch('http://localhost:8888/MAINTENANCE-SERVICE/maintenanceTasks/maintenances/completed');
                const data = await response.json();
                setMaintenances(data);
            } catch (err) {
                setError('Erreur lors de la récupération des maintenances.');
            }
        };
        fetchMaintenances();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!montantTotal || !idMaintenance || !intervention) {
            setError('Tous les champs sont requis.');
            return;
        }
        setError('');

        const factureData = {
            montantTotal,
            id_Maintenanace: idMaintenance,
            intervention,
            etat,
        };

        try {
            const response = await fetch('http://localhost:8888/FACTURE-SERVICE/factures/save', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(factureData),
            });

            if (!response.ok) throw new Error('Erreur lors de la création de la facture.');

            setSuccess('Facture ajoutée avec succès!');
            setMontantTotal('');
            setIdMaintenance('');
            setIntervention('');
            setEtat(false);
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container className="form-container mt-5">
            <h2 className="text-center mb-3">Ajouter une Facture</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}
            <Form onSubmit={handleSubmit} className="shadow p-4 rounded">
                <Form.Group className="mb-3">
                    <Form.Label>Montant Total</Form.Label>
                    <Form.Control
                        type="number"
                        value={montantTotal}
                        onChange={(e) => setMontantTotal(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>ID de Maintenance</Form.Label>
                    <Form.Select
                        value={idMaintenance}
                        onChange={(e) => setIdMaintenance(e.target.value)}
                        required
                    >
                        <option value="">Sélectionner une maintenance</option>
                        {maintenances.map((maintenance) => (
                            <option key={maintenance.id} value={maintenance.id}>
                                {maintenance.id} - {maintenance.vehiculeId || 'N/A'}
                            </option>
                        ))}
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Intervention</Form.Label>
                    <Form.Control
                        type="text"
                        value={intervention}
                        onChange={(e) => setIntervention(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>État</Form.Label>
                    <Form.Check
                        type="checkbox"
                        checked={etat}
                        onChange={() => setEtat(!etat)}
                    />
                </Form.Group>
                <Button variant="primary" className="w-100" type="submit">Ajouter</Button>
            </Form>
        </Container>
    );
};

export default FactureForm;
