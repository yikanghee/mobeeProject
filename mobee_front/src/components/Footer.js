/* eslint-disable */

import React from 'react';

// 부트스트랩
import { Jumbotron, Container } from 'react-bootstrap';

//material-ui
import InstagramIcon from "@material-ui/icons/Instagram";
import TwitterIcon from "@material-ui/icons/Twitter";
import FacebookIcon from "@material-ui/icons/Facebook";


// 사이트 하단의 Footer
const Footer = (props) => {

  return (
    <Jumbotron fluid
      style={{
        marginBottom: 0,
        height: "300px",
        backgroundColor: '#F7FAFC',
        color: '#40474D',
        textAlign: 'center',
        boxSizing: 'border-box',
        fontWegiht: 'bold',
      }}>
      <Container>
        <div className="footer_head" >
          <p>Mobee 홈페이지</p>
        </div>
        <div className='icons' style={{ margin: '25px' }}>
          <InstagramIcon
            style={{
              color: '#636c73',
              fontSize: '22px',
              margin: '0 10px'
            }} />
          <TwitterIcon
            style={{
              color: '#636c73',
              fontSize: '22px',
              margin: '0 10px'
            }} />
          <FacebookIcon
            style={{
              color: '#636c73',
              fontSize: '22px',
              margin: '0 10px'
            }} />
        </div>
        <div style={{
          fontSize: '11px',
          color: '#636c73',
          lineHeight: '17px'

        }}>
        </div>
        <p
          style={{
            padding: '20px 0 0 0',
            fontSize: '11px',
            color: '#636c73',
          }}
        >
          </p>
        <p
          style={{
            padding: '30px 0 30px 0',
            fontSize: '14px',
            color: '#636c73',
          }}>
        </p>
      </Container>
    </Jumbotron>
  )

};

export default Footer;
