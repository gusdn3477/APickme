import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
//import BrandData from '../../../../db/nav.json';

export default function Brand() {

    return (
        <div class="brand-logo">
            {/* <img src="../../images/logo.svg" alt="logo" /> */}
            <Link to="/">Apick Me!</Link>
        </div>
    );
}