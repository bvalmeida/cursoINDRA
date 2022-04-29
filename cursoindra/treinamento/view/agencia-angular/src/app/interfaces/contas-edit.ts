import { ICliente } from './cliente';

export interface IContaEdit {
  id?: number;
  agencia: string;
  numero: string;
  saldo: number;
  cliente?: ICliente;
}
