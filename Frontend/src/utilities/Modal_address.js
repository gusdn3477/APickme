import Postcode from '@actbase/react-daum-postcode';
import { Button, Modal } from '@material-ui/core';
import { useState } from 'react';

const DaumAddress = () => {
  const [isModal, setModal] = useState(false);
  return (
    <>
      <Modal isVisible={isModal}>
        <Postcode
          style={{ width: 320, height: 320 }}
          jsOptions={{ animation: true, hideMapBtn: true }}
          onSelected={data => {
            alert(JSON.stringify(data));
            setModal(false);
          }}
        />
      </Modal>
      <Button onClick={() => setModal(true)}>주소찾기</Button>
    </>
  );
}

export default DaumAddress;