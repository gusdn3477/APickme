import React from 'react';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Dashboard() {

    return (
        <li className="nav-item">
            <Link className="nav-link" to="/">
                <i className="icon-grid menu-icon"></i>
                <span className="menu-title">Dashboard</span>
            </Link>
        </li>
    );
}