"""
import2_mctotal.csv = 100% acceptor (represented by all red)
import2_mc15.csv = 3:1 (acceptor : donor)
import2_mc7.csv = 2:1
import2_mc3.csv = 1:1
import_mc3.csv = 1:1
import_mc7.csv = 1:2
import_mc15.csv = 1:3
import_mctotal.csv = 100% donor (represented by all blue)
"""

# %z_morphblend = linspace(-59.9,0,60);
# %Import empty csv file to create microscale canvas
from sys import argv
import os

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

import time
import threading
import multiprocessing


class OpticalGenerationMicroscale:
    # initialize empty csv file
    # z_morphblend = csvread('z_morphblend3.csv');
    # originally called OpticalGenerationMicroscale.wavelengths

    # d_morphblend = 100
    # d_ITO = 100
    # d_PEDOT = 30
    # d_Al = 100

    # self.d_morphblend = 100
    wavelengths = np.linspace(300, 900, 25)  # ; % wavelength of light in nm
    ITO_n = np.array([2.31048, 2.26177, 2.21058, 2.16118, 2.11522, 2.07358, 2.03596, 2.00244, 1.97235, 1.94559, 1.92156, 1.90013,
                      1.88081, 1.86351, 1.84783, 1.83372, 1.82088, 1.80925, 1.79863, 1.78896, 1.78008, 1.77196, 1.76447, 1.76447, 1.76447])

    ITO_k = np.array([1.0105E-1, 7.4934E-2, 5.8513E-2, 4.8929E-2, 4.3504E-2,
                      4.1097E-2, 4.0563E-2, 4.1402E-2, 4.3090E-2, 4.5391E-2, 4.8057E-2,
                      5.0968E-2, 5.4006E-2, 5.7106E-2, 6.0216E-2, 6.3298E-2, 6.6331E-2,
                      6.9294E-2, 7.2180E-2, 7.4977E-2, 7.7688E-2, 8.0303E-2, 8.2831E-2,
                      8.2831E-2, 8.2831E-2])

    Al_n = np.array([0.26418, 0.31202, 0.36667, 0.42484, 0.48787, 0.55571, 0.63324,
                     0.72122, 0.81257, 0.90906, 1.015192, 1.13226, 1.26232, 1.39838,
                     1.55803, 1.73276, 1.92139, 2.12536, 2.3669, 2.61609, 2.76733,
                     2.73627, 2.58839, 2.37203, 2.111])

    Al_k = np.array([3.5787, 3.9002, 4.2127, 4.5244, 4.8355, 5.1464, 5.4544, 5.7556,
                     6.0481, 6.3387, 6.6273, 6.9099, 7.1855, 7.4570, 7.7124, 7.9374,
                     8.1420, 8.3249, 8.4177, 8.4909, 8.3543, 8.2280, 8.1687, 8.1299,
                     8.2197])

    Pedot_n = np.linspace(1.56, 1.32, 25)

    Pedot_k = np.array([0.015, 0.016, 0.017, 0.019, 0.02, 0.021, 0.024, 0.026, 0.03, 0.035,
                        0.04, 0.045, 0.052, 0.062, 0.072, 0.079, 0.087, 0.097, 0.105, 0.115, 0.12,
                        0.129, 0.134, 0.14, 0.15])

    glass_n = np.array([1.65, 1.62, 1.59, 1.57, 1.56, 1.55, 1.54, 1.53, 1.52, 1.52, 1.52,
                        1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52, 1.52])

    glass_k =np.zeros(25)

    #####

    cupc_n = np.array([1.462, 1.448, 1.434, 1.441, 1.441, 1.473, 1.522, 1.616, 1.762, 1.978, 2.211, 2.368, 2.421,
                       2.388, 2.332, 2.231, 2.185, 2.137, 2.109, 2.070, 2.058, 2.036, 2.029, 2.009, 2.016])

    cupc_k = np.array([0.039, 0.047, 0.066, 0.098, 0.140, 0.195, 0.324, 0.458, 0.628, 0.740, 0.769, 0.661, 0.535,
                       0.346, 0.071, 0.014, 0.010, 0.009, 0.009, 0, 0, 0, 0, 0, 0])

    c60_n = np.array([1.96, 1.97, 2.19, 2.31, 2.24, 2.19, 2.23, 2.32, 2.36, 2.33, 2.29, 2.26,
                      2.235, 2.22, 2.21, 2.205, 2.195, 2.185, 2.183, 2.179, 2.174, 2.17, 2.166,
                      2.163, 2.16])

    c60_k = np.array([0.395, 0.425, 0.51, 0.295, 0.205, 0.21, 0.26, 0.205, 0.155, 0.055,
                      0.025, 0.015, 0.01, 0.007, 0.004, 0.003, 0.002, 0.001, 0, 0, 0, 0, 0, 0, 0])

    ###
    solar_irradiance = np.array([1.0205E-03, 2.7894E-01, 5.2798E-01, 5.8930E-01,
                                 1.1141, 1.2488, 1.5595, 1.6185, 1.5451, 1.5781, 1.5399,
                                 1.4777, 1.4753, 1.4026, 1.3594, 1.3958, 1.2823,
                                 1.0380, 1.2341, 1.1771, 1.0725, 9.6935E-01, 8.9372E-01,
                                 9.2687E-01, 7.4260E-01])  # W/(m^2*nm)

    # V/(m*(nm)^1/2), n = 1
    E_not = np.sqrt(2*solar_irradiance/(3E8*8.854E-12))

    def __init__(self, file_path, working_directory):
        # empty csv files
        self.file_path=file_path
        self.z_morphblend = np.array(pd.read_csv(
            "/Users/abebeamare/Desktop/Desktop/spring2021/ECE493/FinalProject/src/data/z_morphblend3.csv",header=None))  # will be removed
        #print(len(self.z_morphblend))
        #print(self.z_morphblend[0])
        #print(self.z_morphblend[599998])
        self.working_directory=working_directory
        self.d_morphblend = 100
        self.d_ITO = 100
        self.d_PEDOT = 30
        self.d_Al = 100

        # Import csv file representing canvas created from morphology generator code
        self.blend_morphology = np.array(pd.read_csv(file_path, header=None))

    def multi_process(self,n):
        #print("started loop number "+str(n))
        pi = np.pi

        T = 0
        R = 0
        Tstar = 0
        Rstar = 0
        Tprime = 0
        Rprime = 0

        # Import csv file representing canvas created from morphology generator code

        t_morphblend_plus = 0
        t_morphblend_minus = 0
        E_morphblend = 0
        exci_gen_morphblend = 0

        conv_total_ex_gen_morphblend = np.zeros(600000)
        total_ex_gen_morphblend=np.zeros(600000)
        morphology_blend_n=1 #place holder to declare the variable
        morphology_blend_k=1
        for m in range(600000):
            if (self.blend_morphology[m][3] == -1):
                morphology_blend_n = OpticalGenerationMicroscale.c60_n[n]
                morphology_blend_k = OpticalGenerationMicroscale.c60_k[n]
            else:
                morphology_blend_n = OpticalGenerationMicroscale.cupc_n[n]
                morphology_blend_k = OpticalGenerationMicroscale.cupc_k[n]

            glass = OpticalGenerationMicroscale.glass_n[n] + \
                    1j*OpticalGenerationMicroscale.glass_k[n]
            #print("glass")
            #print(glass)

            ITO = OpticalGenerationMicroscale.ITO_n[n] + \
                  1j*OpticalGenerationMicroscale.ITO_k[n]

            PEDOT = OpticalGenerationMicroscale.Pedot_n[n] + \
                    1j*OpticalGenerationMicroscale.Pedot_k[n]

            morphblend = morphology_blend_n + \
                         1j*morphology_blend_k

            Al = OpticalGenerationMicroscale.Al_n[n] + \
                 1j*OpticalGenerationMicroscale.Al_k[n]

            alpha_morphblend = (4*pi*morphology_blend_k)/(OpticalGenerationMicroscale.wavelengths[n])

            #print("alpha_morphblend")
            #print(alpha_morphblend)

            I_glass_ITO = np.array([[(glass+ITO)/(2*glass), (glass-ITO)/(2*glass)],
                                    [(glass-ITO)/(2*glass), (glass+glass)/(2*glass)]])

            #print("I_glass_ITO")
            #print(I_glass_ITO)

            I_ITO_PEDOT = np.array([[(ITO+PEDOT)/(2*ITO), (ITO-PEDOT)/(2*ITO)],
                                    [(ITO-PEDOT)/(2*ITO), (ITO+PEDOT)/(2*ITO)]])
            #print("I_ITO_PEDOT")
            #print(I_ITO_PEDOT)

            I_PEDOT_morphblend = np.array([[(PEDOT+morphblend)/(2*PEDOT), (PEDOT-morphblend)/(2*PEDOT)],
                                           [(PEDOT-morphblend)/(2*PEDOT), (PEDOT+morphblend)/(2*PEDOT)]])

            #print("I_PEDOT_morphblend")
            #print(I_PEDOT_morphblend)

            I_morphblend_morphblend = np.array([[(morphblend+morphblend)/(2*morphblend), (morphblend-morphblend)/(2*morphblend)],
                                                [(morphblend-morphblend)/(2*morphblend), (morphblend+morphblend)/(2*morphblend)]])
            #print("I_morphblend_morphblend")
            #print(I_morphblend_morphblend)

            I_morphblend_Al = np.array([[(morphblend+Al)/(2*morphblend), (morphblend-Al)/(2*morphblend)],
                                        [(morphblend-Al)/(2*morphblend), (morphblend+Al)/(2*morphblend)]])

            #print("I_morphblend_Al")
            #print(I_morphblend_Al)
            I_Al_air = np.array([[(Al+1)/(2*Al), (Al-1)/(2*Al)],
                                 [(Al-1)/(2*Al), (Al+1)/(2*Al)]])
            #print("I_Al_air")
            #print(I_Al_air)

            L_ITO = np.array([[np.exp(self.d_ITO*-1j*2*pi*ITO/OpticalGenerationMicroscale.wavelengths[n]), 0],
                              [0, np.exp(self.d_ITO*1j*2*pi*ITO/OpticalGenerationMicroscale.wavelengths[n])]])

            #print("L_ITO")
            #print(L_ITO)

            L_PEDOT = np.array([[np.exp(self.d_PEDOT*-1j*2*pi*PEDOT/OpticalGenerationMicroscale.wavelengths[n]), 0],
                                [0, np.exp(self.d_PEDOT*1j*2*pi*PEDOT/OpticalGenerationMicroscale.wavelengths[n])]])

            #print("L_PEDOT")
            #print(L_PEDOT)

            L_morphblend = np.array([[np.exp(self.d_morphblend*-1j*2*pi*morphblend/OpticalGenerationMicroscale.wavelengths[n]), 0],
                                     [0, np.exp(self.d_morphblend*1j*2*pi*morphblend/OpticalGenerationMicroscale.wavelengths[n])]])

            #print("L_morphblend")
            #print(L_morphblend)

            L_Al = np.array([[np.exp(self.d_Al*-1j*2*pi*Al/OpticalGenerationMicroscale.wavelengths[n]), 0],
                             [0, np.exp(self.d_Al*1j*2*pi*Al/OpticalGenerationMicroscale.wavelengths[n])]])
            #print("L_Al")
            #print(L_Al)

            S_morphblend_plus=I_morphblend_Al.dot(L_Al).dot(I_Al_air)
            #print("S_morphblend_plus")
            #print(S_morphblend_plus)
            S_morphblend_minus=I_glass_ITO.dot(L_ITO).dot(I_ITO_PEDOT).dot(L_PEDOT).dot(I_PEDOT_morphblend)
            #print("S_morphblend_minus")
            #print(S_morphblend_minus)
            S=S_morphblend_minus.dot(L_morphblend).dot(S_morphblend_plus)
            #print("S")
            #print(S)


            t_morphblend_plus= (1/S_morphblend_minus[0][0])/(1+(S_morphblend_minus[0][1]*S_morphblend_plus[1][0])/(
                    S_morphblend_minus[0][0]*S_morphblend_plus[0][0])*np.exp(self.d_morphblend*2j*2*pi*morphblend/OpticalGenerationMicroscale.wavelengths[n]))

            #print("t_morphblend_plus")
            #print(t_morphblend_plus)

            t_morphblend_minus = t_morphblend_plus*S_morphblend_plus[1][0]/S_morphblend_plus[0][0]*np.exp(
                self.d_morphblend*2j*2*pi*morphblend/OpticalGenerationMicroscale.wavelengths[n])

            #print("t_morphblend_minus")
            #print(t_morphblend_minus)

            E_morphblend = (t_morphblend_plus*np.exp(self.z_morphblend[m]*1j*2*pi*morphblend/OpticalGenerationMicroscale.wavelengths[n]) + t_morphblend_minus*np.exp(self.z_morphblend[m]*-1j*2*pi*morphblend/OpticalGenerationMicroscale.wavelengths[n]))*OpticalGenerationMicroscale.E_not[n]


            #print("E_morphblend")
            #print(E_morphblend)

            exci_gen_morphblend = OpticalGenerationMicroscale.wavelengths[n]*1E-9/6.626E-34 * \
                                  morphology_blend_n*8.854E-12 * \
                                  (np.abs(E_morphblend))**2*alpha_morphblend

            #print("exci_gen_morphblend")
            #print(exci_gen_morphblend)

            total_ex_gen_morphblend[m] = 600 * \
                                         exci_gen_morphblend + \
                                         total_ex_gen_morphblend[m]
            #print("total_ex_gen_morphblend")
            #print(total_ex_gen_morphblend)

            conv_total_ex_gen_morphblend[m] = total_ex_gen_morphblend[m]*(
                100) ** 2*(1E-9)**2

            #print("conv_total_ex_gen_morphblend")
            #print(conv_total_ex_gen_morphblend[m])

            r = S[1][0]/S[0][0]
            t = S[0][0]**(-1)

            R = (np.abs(r)) ** 2

            T= (1/glass)*(np.abs(t))**2

            Tstar = glass*(np.abs(2/(1+glass)))**2
            Rstar = (np.abs((1-glass)/(1+glass)))**2
            if(m%100000==0):
                print("Process "+str(n)+" reached "+str(m))
            #initialize
            t_morphblend_plus = 0
            t_morphblend_minus = 0
            E_morphblend = 0
            exci_gen_morphblend = 0

        return (Rstar, R, Tstar,T,total_ex_gen_morphblend)
    def run(self):
        self.update_values_before_write()
        self.computed_values()
        self.five_columns_canvas_generator()

    def update_values_before_write(self):
        val=25
        pool = multiprocessing.Pool(processes = val)
        self.all_tuples=pool.map(self.multi_process, range(25))
    def computed_values(self):
        val=25
        self.T = np.zeros(val)
        self.R = np.zeros(val)
        self.Tstar = np.zeros(val)
        self.Rstar = np.zeros(val)
        self.total_ex_gen_morphblend_all=np.zeros((val,600000))
        all_tuples=self.constants()
        for i in range(val):
            self.Rstar[i]=all_tuples[i][0]
            self.R[i]=all_tuples[i][1]
            self.Tstar[i]=all_tuples[i][2]
            self.T[i]=all_tuples[i][3]
            self.total_ex_gen_morphblend_all[i]=all_tuples[i][4]

    def five_columns_canvas_generator(self):
        name=os.path.basename(self.file_path)
        name="/"+name.split(".")[0]
        if not os.path.exists(self.working_directory+'/structures'):
            os.makedirs(self.working_directory+'/microscales')
            self.working_directory=self.working_directory+'/microscales'
        if not os.path.exists(self.working_directory+name):
            os.makedirs(self.working_directory+name)
            self.working_directory=self.working_directory+name

        val=25
        for i in range(val):
            file_i=self.blend_morphology
            file_i=np.column_stack((file_i, self.total_ex_gen_morphblend_all[i]))
            DF = pd.DataFrame(file_i)
            DF.to_csv(self.working_directory+"/microscales_"+str(i)+".csv",header=False, index=False)

    def constants(self):
        return self.all_tuples

    def plotting(self):
        Rprime = (self.Rstar+self.R-2*self.R*self.Rstar)/(1-self.Rstar*self.R)
        Tprime = (self.Tstar*self.T)/(1-self.Rstar*self.R)
        efficiency = 1 - Rprime - Tprime
        plt.plot(self.wavelengths, efficiency)
        plt.savefig('temp.png')
        plt.xlabel('Wavelength (nm)')
        plt.ylabel('Absorption')
        plt.show()

if __name__ == '__main__':
    file_path=argv[1]
    working_directory=argv[2]
    microscale=OpticalGenerationMicroscale(file_path,working_directory)
    microscale.run()
