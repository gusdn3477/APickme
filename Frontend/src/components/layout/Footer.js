// import FooterTitle from "../elements/ui/FooterTitle";
// import FooterSubscribe from "../elements/ui/FooterSubscribe";
// import FooterList from "../elements/widgets/Footer/FooterList";
// import FooterMenuData from "../../db/footermenu.json";
import React, { useState } from "react";

export default function Footer() {
  // const [newFooterMenu, setNewFooterMenu] = useState(FooterMenuData);

  // const footerData1 = newFooterMenu["ABOUT US"].filter(
  // (item) => item === "ABOUT US"
  // );

  return (
    <footer class="footer">
      <div class="d-sm-flex justify-content-center justify-content-sm-between">
        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">TmaxCloud 3조</span>
          {/* <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin template</a> from BootstrapDash. All rights reserved.</span>
        <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made with <i class="ti-heart text-danger ml-1"></i></span> */}
      </div>
      <div class="d-sm-flex justify-content-center justify-content-sm-between">
        <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Distributed by 권진희, 김영모, 윤희상, 박현우
        </span>
      </div>
    </footer>
  );
}
