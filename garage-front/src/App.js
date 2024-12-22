// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './Components/Navbar';
import Client from './Pages/Client';           // Import your ClientList component
import ClientForm from './Components/Client/ClientForm';
import ClientUpdate from "./Components/Client/ClientUpdate";
import Vehicule from "./Pages/Vehicule";
import VehiculeForm from "./Components/VehiculeComponent/VehiculeForm";
import VehiculeUpdate from "./Components/VehiculeComponent/VehiculeUpdate";           // Import your ClientForm component
import Maintenance from "./Pages/Maintenance"; // Ensure this path is correct
import MaintenanceUpdate from "./Components/Maintenance/MaintenanceUpdate";  
import MaintenanceForm from "./Components/Maintenance/MaintenanceForm";
import Notification from "./Pages/Notification";
import NotificationForm from "./Components/Notification/NotificationForm";
import NotificationUpdate from "./Components/Notification/NotificationUpdate";
import Acceuil from "./Pages/Acceuil";
import Facture from "./Pages/Facture";
import FactureForm from "./Components/Facture/FactureForm";           // Import your ClientForm component


const App = () => {
    return (
        <Router>
            <Navbar/>
            <Routes>
                <Route path="/" element={<Acceuil />} />
                <Route path="/clients" element={<Client />} />
                <Route path="/add-client" element={<ClientForm />} />
                {/* Add other routes as needed */}
                <Route path="/clients/update/:cin" element={<ClientUpdate />} /> {/* Route pour mise à jour */}
                <Route path="/vehicules" element={<Vehicule />} />
                <Route path="/add-vehicule" element={<VehiculeForm />} />
                <Route path="/vehicules/update/:vin" element={<VehiculeUpdate />} /> {/* Route pour mise à jour */}
                <Route path="/maintenances" element={<Maintenance />} />
                <Route path="/maintenances/new" element={<MaintenanceForm />} />
                <Route path="/maintenances/update/:id" element={<MaintenanceUpdate />} />
                <Route path="/notification" element={<Notification />} />
                <Route path="/notification/new" element={<NotificationForm />} />
                <Route path="/notification/update/:id" element={<NotificationUpdate />} />
                <Route path="/factures" element={<Facture />} />
                <Route path="/factures/new" element={<FactureForm />} />



            </Routes>
        </Router>
    );
};

export default App;
