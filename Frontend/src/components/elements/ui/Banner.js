import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bootstrap-icons/font/bootstrap-icons.css";
import Dashboard from "../widgets/BannerMenu/Dashboard";
import Jobs from "../widgets/BannerMenu/Jobs";
import HRAccount from "../widgets/BannerMenu/HRAccount";
import FindJobs from "../widgets/BannerMenu/FindJobs";

export default function Banner() {
  if (localStorage.getItem("empNo")) {
    // 인사담당자인 경우
    return (
      <nav
        className="sidebar sidebar-offcanvas"
        id="sidebar"
        style={{ width: "300px" }}
      >
        <ul className="nav">
          <Dashboard />
          <HRAccount />
          <Jobs />
          <li className="nav-item">
            <a
              className="nav-link"
              data-bs-toggle="collapse"
              href="#process-control"
              aria-expanded="false"
              aria-controls="process-control"
            >
              <i className="icon-layout menu-icon"></i>
              <span className="menu-title">전형관리</span>
              <i className="menu-arrow"></i>
            </a>
            <div className="collapse" id="process-control">
              <ul className="nav flex-column sub-menu">
                <li className="nav-item">
                  <Link className="nav-link" to="/process/written">
                    필기 전형
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/process/firstinterview">
                    1차 면접 전형
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/process/secondinterview">
                    2차 면접 전형
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/process/final">
                    지원자 명단
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/process/pass">
                    합격자 명단
                  </Link>
                </li>
              </ul>
            </div>
          </li>
          <li className="nav-item">
            <a
              className="nav-link"
              data-bs-toggle="collapse"
              href="#mypage"
              aria-expanded="false"
              aria-controls="mypage"
            >
              <i className="icon-layout menu-icon"></i>
              <span className="menu-title">마이페이지</span>
              <i className="menu-arrow"></i>
            </a>
            <div className="collapse" id="mypage">
              <ul className="nav flex-column sub-menu">
                <li className="nav-item">
                  <Link className="nav-link" to="/hr/check-password">
                    내 정보 수정하기
                  </Link>
                </li>
              </ul>
            </div>
          </li>
          <li className="nav-item">
            <a
              className="nav-link"
              data-bs-toggle="collapse"
              href="#statistics"
              aria-expanded="false"
              aria-controls="statistics"
            >
              <i className="icon-layout menu-icon"></i>
              <span className="menu-title">통계자료</span>
              <i className="menu-arrow"></i>
            </a>
            <div className="collapse" id="statistics">
              <ul className="nav flex-column sub-menu">
                <li className="nav-item">
                  <Link className="nav-link" to="/statistics">
                    공고별 지원 현황
                  </Link>
                </li>
              </ul>
            </div>
          </li>
        </ul>
        {/* 부트스트랩 5는 되는데 4는 토글이 안됨. */}
      </nav>
    );
  } else if (localStorage.getItem("userId")) {
    return (
      <nav
        className="sidebar sidebar-offcanvas"
        id="sidebar"
        style={{ width: "300px" }}
      >
        <ul className="nav">
          <Dashboard />
          <FindJobs />
          <li className="nav-item">
            <a
              className="nav-link"
              data-bs-toggle="collapse"
              href="#mypage"
              aria-expanded="false"
              aria-controls="mypage"
            >
              <i className="icon-layout menu-icon"></i>
              <span className="menu-title">마이페이지</span>
              <i className="menu-arrow"></i>
            </a>
            <div className="collapse" id="mypage">
              <ul className="nav flex-column sub-menu">
                <li className="nav-item">
                  <Link className="nav-link" to="/user/check-password">
                    내 정보 수정
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/user/mylist">
                    나의 지원 현황
                  </Link>
                </li>
              </ul>
            </div>
          </li>
        </ul>
        {/* 부트스트랩 5는 되는데 4는 토글이 안됨. */}
      </nav>
    );
  }
}
