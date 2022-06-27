import React from "react";
import styled from "styled-components";

// 그리드 컴포넌트
const Grid = (props) => {
  const { children } = props

  return (
    <React.Fragment>
      <GridBox>{children}</GridBox>
    </React.Fragment>
  );
};

Grid.defaultProps = {
  chidren: null,
  is_flex: false,
  width: '100%',
  padding: false,
  margin: false,
  bg: false,
  center: false,
  left: false,
  _onClick: () => {}
};

const GridBox = styled.div`
width: ${(props) => props.width};
  height: 100%;
  box-sizing: border-box;
  ${(props) => (props.padding ? `padding: ${props.padding};` : '')}
  ${(props) => (props.margin ? `margin: ${props.margin};` : '')}
  ${(props) => (props.bg ? `background-color: ${props.bg};` : '')}
  ${(props) =>
    props.is_flex
      ? `display: flex; align-items: center; justify-content: space-between; `
      : ''}
  ${(props) => (props.center ? `text-align: center;` : '')}
  ${(props) => (props.left ? `text-align: left;` : '')}
  ${(props) => (props.align_items_center ? `align-items: center;` : '')}
`;


export default Grid;