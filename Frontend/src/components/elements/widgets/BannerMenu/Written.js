import React from 'react';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Written() {

  return (

    <li className="nav-item">
      <a className="nav-link" data-bs-toggle="collapse" href="#process-control" aria-expanded="false" aria-controls="process-control">
        <i className="icon-layout menu-icon"></i>
        <span className="menu-title">필기전형관리(인사담당자)</span>
        <i className="menu-arrow"></i>
      </a>
      <div className="collapse" id="process-control">
        <ul className="nav flex-column sub-menu">
          <li className="nav-item"><Link className="nav-link" to="/process/written">필기 전형 채점</Link></li>
        </ul>
      </div>
    </li>

  );
}