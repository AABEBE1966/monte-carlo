//package JavaSimulation.src;
package model;

public class ExcitonGenerator {

	public int[] genExciton(int xdim, int ydim) {
		int x = RandomGenerator.randIndex(xdim);
		int y = RandomGenerator.randIndex(ydim);
		int z = 0;
		double r = RandomGenerator.randDouble();
		if (r <	0.013994657	) { z =	0	;
		} else if (r <	0.027774048	) { z = 	1	;
		} else if (r <	0.041360259	) { z = 	2	;
		} else if (r <	0.054775348	) { z = 	3	;
		} else if (r <	0.068041298	) { z = 	4	;
		} else if (r <	0.081179981	) { z = 	5	;
		} else if (r <	0.094213113	) { z = 	6	;
		} else if (r <	0.107162214	) { z = 	7	;
		} else if (r <	0.120048566	) { z = 	8	;
		} else if (r <	0.132893172	) { z = 	9	;
		} else if (r <	0.145716713	) { z = 	10	;
		} else if (r <	0.15853951	) { z = 	11	;
		} else if (r <	0.171381483	) { z = 	12	;
		} else if (r <	0.18426211	) { z = 	13	;
		} else if (r <	0.197200391	) { z = 	14	;
		} else if (r <	0.210214807	) { z = 	15	;
		} else if (r <	0.223323282	) { z = 	16	;
		} else if (r <	0.236543152	) { z = 	17	;
		} else if (r <	0.249891123	) { z = 	18	;
		} else if (r <	0.263383242	) { z = 	19	;
		} else if (r <	0.277034863	) { z = 	20	;
		} else if (r <	0.290860611	) { z = 	21	;
		} else if (r <	0.304874358	) { z = 	22	;
		} else if (r <	0.319089192	) { z = 	23	;
		} else if (r <	0.333517389	) { z = 	24	;
		} else if (r <	0.348170386	) { z = 	25	;
		} else if (r <	0.363058762	) { z = 	26	;
		} else if (r <	0.37819221	) { z = 	27	;
		} else if (r <	0.393579522	) { z = 	28	;
		} else if (r <	0.409228567	) { z = 	29	;
		} else if (r <	0.425146277	) { z = 	30	;
		} else if (r <	0.441338628	) { z = 	31	;
		} else if (r <	0.457810636	) { z = 	32	;
		} else if (r <	0.474566338	) { z = 	33	;
		} else if (r <	0.491608788	) { z = 	34	;
		} else if (r <	0.508940052	) { z = 	35	;
		} else if (r <	0.526561202	) { z = 	36	;
		} else if (r <	0.544472312	) { z = 	37	;
		} else if (r <	0.562672467	) { z = 	38	;
		} else if (r <	0.581159756	) { z = 	39	;
		} else if (r <	0.599931284	) { z = 	40	;
		} else if (r <	0.618983175	) { z = 	41	;
		} else if (r <	0.638310586	) { z = 	42	;
		} else if (r <	0.657907715	) { z = 	43	;
		} else if (r <	0.677767817	) { z = 	44	;
		} else if (r <	0.69788322	) { z = 	45	;
		} else if (r <	0.718245342	) { z = 	46	;
		} else if (r <	0.738844716	) { z = 	47	;
		} else if (r <	0.759671007	) { z = 	48	;
		} else if (r <	0.780713042	) { z = 	49	;
		} else if (r <	0.801958834	) { z = 	50	;
		} else if (r <	0.823395611	) { z = 	51	;
		} else if (r <	0.845009848	) { z = 	52	;
		} else if (r <	0.8667873	) { z = 	53	;
		} else if (r <	0.888713035	) { z = 	54	;
		} else if (r <	0.910771471	) { z = 	55	;
		} else if (r <	0.932946414	) { z = 	56	;
		} else if (r <	0.955221097	) { z = 	57	;
		} else if (r <	0.977578222	) { z = 	58	;
		} else if (r <	1	) { z = 	59	;}
		int[] ret = {x,y,z};
		return ret;
	}


	public double getExcitonRate() {
		return 45.13551686; //blend
		//return 24.50691918; //bilayer
	}
}
