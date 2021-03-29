%Generates absorption data for your structure

function total_ex_gen_morphblend= OpticalGenrationMicroScaleAlt(thiscanvas)
    tic
    d_morphblend = 100;

    d_ITO = 100;
    d_PEDOT = 30;
    d_Al = 100;

    %z_morphblend = linspace(-59.9,0,60);
    %Import empty csv file to create microscale canvas
    z_morphblend = csvread('z_morphblend3.csv');

    lambda = linspace(300,900,25); % wavelength of light in nm
    ITO_n = [2.31048, 2.26177, 2.21058, 2.16118, 2.11522, 2.07358, 2.03596, ...
        2.00244, 1.97235, 1.94559, 1.92156, 1.90013, 1.88081, 1.86351, ...
        1.84783, 1.83372, 1.82088, 1.80925, 1.79863, 1.78896, 1.78008, ...
        1.77196, 1.76447, 1.76447, 1.76447];
    ITO_k = [1.0105E-1, 7.4934E-2, 5.8513E-2, 4.8929E-2, 4.3504E-2, ...
        4.1097E-2, 4.0563E-2, 4.1402E-2, 4.3090E-2, 4.5391E-2, 4.8057E-2, ...
        5.0968E-2, 5.4006E-2, 5.7106E-2, 6.0216E-2, 6.3298E-2, 6.6331E-2, ...
        6.9294E-2, 7.2180E-2, 7.4977E-2, 7.7688E-2, 8.0303E-2, 8.2831E-2, ...
        8.2831E-2, 8.2831E-2];
    Al_n = [0.26418, 0.31202, 0.36667, 0.42484, 0.48787, 0.55571, 0.63324, ...
        0.72122, 0.81257, 0.90906, 1.015192, 1.13226, 1.26232, 1.39838, ...
        1.55803, 1.73276, 1.92139, 2.12536, 2.3669, 2.61609, 2.76733, ...
        2.73627, 2.58839, 2.37203, 2.111];
    Al_k = [3.5787, 3.9002, 4.2127, 4.5244, 4.8355, 5.1464, 5.4544, 5.7556,...
        6.0481, 6.3387, 6.6273, 6.9099, 7.1855, 7.4570, 7.7124, 7.9374, ...
        8.1420, 8.3249, 8.4177, 8.4909, 8.3543, 8.2280, 8.1687, 8.1299, ...
        8.2197];
    Pedot_n = linspace(1.56,1.32,25);
    Pedot_k = [0.015,0.016,0.017,0.019,0.02,0.021,0.024,0.026,0.03,0.035,...
        0.04,0.045,0.052,0.062,0.072,0.079,0.087,0.097,0.105,0.115,0.12,...
        0.129,0.134,0.14,0.15];
    glass_n = [1.65,1.62,1.59,1.57,1.56,1.55,1.54,1.53,1.52, 1.52,1.52, ...
        1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52,1.52];
    glass_k = zeros(1,25);


    cupc_n = [1.462,1.448,1.434,1.441,1.441,1.473,1.522,1.616,1.762,1.978,2.211,2.368,2.421,...
        2.388,2.332,2.231,2.185,2.137,2.109,2.070,2.058,2.036,2.029,2.009,2.016];
    cupc_k = [0.039,0.047,0.066,0.098,0.140,0.195,0.324,0.458,0.628,0.740,0.769,0.661,0.535,...
        0.346,0.071,0.014,0.010,0.009,0.009,0,0,0,0,0,0];
    c60_n = [1.96,1.97,2.19,2.31,2.24,2.19,2.23,2.32,2.36,2.33,2.29,2.26,...
        2.235,2.22,2.21,2.205,2.195,2.185,2.183,2.179,2.174,2.17,2.166,...
        2.163,2.16];
    c60_k = [0.395,0.425,0.51,0.295,0.205,0.21,0.26,0.205,0.155,0.055,...
        0.025,0.015,0.01,0.007,0.004,0.003,0.002,0.001,0,0,0,0,0,0,0];

    %Import csv file representing canvas created from morphology generator code
    %thiscanvas = input('Enter canvas name: ', 's');
    %thiscanvas="structure26.csv";
    blend_morphology = csvread(thiscanvas);
    morphology_blend_n= zeros(600000,25,1);
    morphology_blend_k= zeros(600000,25,1);

    for m =1:600000

        for n = 1:25
            if blend_morphology(m,4) == -1
                morphology_blend_n(m,n) = c60_n(n);
                morphology_blend_k(m,n) = c60_k(n);
            else
                morphology_blend_n(m,n) = cupc_n(n);
                morphology_blend_k(m,n) = cupc_k(n);
            end
        end

    end

    solar_irradiance = [1.0205E-03, 2.7894E-01, 5.2798E-01, 5.8930E-01,...
        1.1141, 1.2488, 1.5595, 1.6185, 1.5451, 1.5781, 1.5399, ...
        1.4777, 1.4753, 1.4026, 1.3594, 1.3958, 1.2823, ...
        1.0380, 1.2341, 1.1771, 1.0725, 9.6935E-01, 8.9372E-01, ...
        9.2687E-01, 7.4260E-01]; %W/(m^2*nm)
    E_not = sqrt(2.*solar_irradiance./(3E8*8.854E-12)); %V/(m*(nm)^1/2), n = 1

    T = zeros(1,25);
    R = zeros(1,25);
    Tstar = zeros(1,25);
    Rstar = zeros(1,25);
    Tprime = zeros(1,25);
    Rprime = zeros(1,25);

    t_morphblend_plus = zeros(600000,25);
    t_morphblend_minus = zeros(600000,25);
    E_morphblend = zeros(600000,25);
    exci_gen_morphblend = zeros(600000,25);


    total_ex_gen_morphblend = zeros(600000,25);
    conv_total_ex_gen_morphblend = zeros(600000,25);


    for n=1:25

        for m=1:600000

            glass = glass_n(n)+1i*glass_k(n);
            ITO = ITO_n(n)+1i*ITO_k(n);
            PEDOT = Pedot_n(n)+1i*Pedot_k(n);

            morphblend = morphology_blend_n(m,n)+1i*morphology_blend_k(m,n);

            Al = Al_n(n)+1i*Al_k(n);

            alpha_morphblend = (4*pi*morphology_blend_k(m,n))/(lambda(n));

            I_glass_ITO = [(glass+ITO)/(2*glass), (glass-ITO)/(2*glass);...
                (glass-ITO)/(2*glass), (glass+glass)/(2*glass)];

            I_ITO_PEDOT = [(ITO+PEDOT)/(2*ITO), (ITO-PEDOT)/(2*ITO); ...
                (ITO-PEDOT)/(2*ITO), (ITO+PEDOT)/(2*ITO)];

            I_PEDOT_morphblend = [(PEDOT+morphblend)/(2*PEDOT), (PEDOT-morphblend)/(2*PEDOT);...
                (PEDOT-morphblend)/(2*PEDOT), (PEDOT+morphblend)/(2*PEDOT)];

            I_morphblend_morphblend = [(morphblend+morphblend)/(2*morphblend), (morphblend-morphblend)/(2*morphblend); ...
                (morphblend-morphblend)/(2*morphblend), (morphblend+morphblend)/(2*morphblend)];

            I_morphblend_Al = [(morphblend+Al)/(2*morphblend), (morphblend-Al)/(2*morphblend); ...
                (morphblend-Al)/(2*morphblend), (morphblend+Al)/(2*morphblend)];

            I_Al_air = [(Al+1)/(2*Al), (Al-1)/(2*Al); ...
                (Al-1)/(2*Al), (Al+1)/(2*Al)];

            L_ITO = [exp(d_ITO*-1i*2*pi*ITO/lambda(n)), 0; ...
                0, exp(d_ITO*1i*2*pi*ITO/lambda(n))];
            L_PEDOT = [exp(d_PEDOT*-1i*2*pi*PEDOT/lambda(n)), 0; ...
                0, exp(d_PEDOT*1i*2*pi*PEDOT/lambda(n))];


            L_morphblend = [exp(d_morphblend*-1i*2*pi*morphblend/lambda(n)), 0; ...
                0, exp(d_morphblend*1i*2*pi*morphblend/lambda(n))];

            L_Al = [exp(d_Al*-1i*2*pi*Al/lambda(n)), 0; ...
                0, exp(d_Al*1i*2*pi*Al/lambda(n))];

            S = I_glass_ITO*L_ITO*I_ITO_PEDOT*L_PEDOT*I_PEDOT_morphblend*L_morphblend...
                *I_morphblend_Al*L_Al*I_Al_air;


            S_morphblend_plus = I_morphblend_Al*L_Al*I_Al_air;
            S_morphblend_minus = I_glass_ITO*L_ITO*I_ITO_PEDOT*L_PEDOT*I_PEDOT_morphblend;


            t_morphblend_plus(m,n) = (1/S_morphblend_minus(1,1))/(1+(S_morphblend_minus(1,2)*...
                S_morphblend_plus(2,1))/(S_morphblend_minus(1,1)*S_morphblend_plus(1,1))*...
                exp(d_morphblend*2i*2*pi*morphblend/lambda(n)));

            t_morphblend_minus(m,n) = t_morphblend_plus(m,n)*S_morphblend_plus(2,1)/S_morphblend_plus(1,1)*...
                exp(d_morphblend*2i*2*pi*morphblend/lambda(n));


            E_morphblend(m,n) = (t_morphblend_plus(m,n).*exp(z_morphblend(m).*1i.*2.*pi.*morphblend./lambda(n))+...
                t_morphblend_minus(m,n).*exp(z_morphblend(m).*-1i.*2.*pi.*morphblend./lambda(n)))*E_not(n);


            exci_gen_morphblend(m,n) = lambda(n)*1E-9/6.626E-34*... %1/energy of photon
                morphology_blend_n(m,n)*8.854E-12*(abs(E_morphblend(m,n))).^2*alpha_morphblend;
            total_ex_gen_morphblend(m,n) = 600*exci_gen_morphblend(m,n)+total_ex_gen_morphblend(m,n);

            %the following matrix should be copied to give spectral dependent exciton generation profile for microscale simulations
            conv_total_ex_gen_morphblend(m,n) = total_ex_gen_morphblend(m,n)*(100)^2*(1E-9)^2;

            r = S(2,1)/S(1,1);
            t = S(1,1)^(-1);
            R(n) = (abs(r))^2;
            T(n) = (1/glass)*(abs(t))^2;
            Tstar(n) = glass*(abs(2/(1+glass)))^2;
            Rstar(n) = (abs((1-glass)/(1+glass)))^2;

        end
    end

    Rprime = (Rstar+R-2.*R.*Rstar)./(1-Rstar.*R);
    Tprime = (Tstar.*T)./(1-Rstar.*R);
    efficiency = 1 - Rprime - Tprime;
    plot(lambda, efficiency), xlabel('Wavelength (nm)'), ylabel('Absorption')
    toc

end
