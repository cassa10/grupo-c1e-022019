import React from 'react';
import { useTranslation } from 'react-i18next';
import Button from 'react-bootstrap/Button';

export default function ChangeLenguage() {
  const { t, i18n } = useTranslation();

  const changeLanguage = (lng) => {
    i18n.changeLanguage(lng);
  };
  return (
      <div>
      <button onClick={() => changeLanguage('es')}>spanish</button>
      <button onClick={() => changeLanguage('en')}>english</button>
      </div>
  );
}
